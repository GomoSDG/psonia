(ns psonia.panels.cart.components
  (:require [re-frame.core :as re-frame]
            [psonia.panels.cart.data]
            [psonia.models.address :as address]
            [psonia.urls :refer [resolve-href]]
            [reagent.ratom :as ratom]))

(defn address-selector
  []
  (let [addresses          (re-frame/subscribe [:psonia.cart/addresses])
        current-address-id (re-frame/subscribe [:psonia.cart/current-address])
        address-ids        (map (comp first vals) (keys @addresses))
        indexed-addresses  (map vector address-ids (map first (vals @addresses)))]
    (fn []
      [:<>
       [:h2.h6.pb-3.mb-2
        "Choose shipping address"]
       [:div.table-responsive
        [:table.table.table-hover.font-size-sm.border-bottom
         [:thead
          [:tr
           [:th.align-middle]
           [:th.align-middle "Address"]]]
         [:tbody
          (doall
            (for [[adr-id adr] indexed-addresses]
              [:tr
               [:td
                [:div.custom-control.custom-radio.mb-4
                 [:input.custom-control-input
                  {:type      "radio"
                   :id        (str adr-id)
                   :name      "shipping-address"
                   :checked   (= @current-address-id adr-id)
                   :on-change #(when (-> (.-target %)
                                         (.-checked))
                                 (re-frame/dispatch [:psonia.cart/set-shipping-address adr-id]))}]
                 [:label.custom-control-label
                  {:for (str adr-id)}]]]
               [:td
                (address/->address-string adr)]]))]]]])))

(def qty-units {:rate "hr"})

(defn- quantity
  "Gives quantity based on quantity type."
  [{:keys [on-change type end]}]
  (let []
    (fn [options value]
      [:select.custom-select.mr-3
       {:on-change #(on-change (-> (.-target %)
                                   (.-value)
                                   int))
        :value     (or (options :val) 1)}
       (for [o (range 1 (inc end))]
         ^{:key o}
         [:option {:value o}
          (str o " " (qty-units type))])])))

(defn add-to-cart-btn
  "Adds products/services to cart"
  ([options product]
   (let [qty          (ratom/atom 1)
         update-value #(reset! qty %)]
     (fn [options product]
       [:div.form-group.row
        [:div
         {:class (case (:qty-position options)
                   :top  ["col-12"]
                   :side ["col-sm-12" "col-md-3"]
                   ["col-sm-12" "col-md-3"])}
         [quantity
          {:type      (:quantity product)
           :end       5
           :on-change update-value}
          qty]]
        [:div
         {:class (case (:qty-position options)
                   :top  ["col-12"]
                   :side ["col-sm-12" "col-md-9"]
                   ["col-sm-12" "col-md-9"])}
         [:button.btn.btn-primary.btn-sm.mt-1
          {:class    (or (:button-classes options) [])
           :on-click #(re-frame/dispatch [:psonia.cart/add-to-cart @qty product])}
          "Add to Cart"]]])))
  ([product]
   (add-to-cart-btn {} product)))

(defn product-quantity
  "Updates the amount of products that are in the cart."
  [qty product]
  [quantity {:on-change #(re-frame/dispatch [:psonia.cart/update-product-qty % product])
             :end       5
             :val       qty
             :type      (:quantity product)}])

(def steps [{:id   :cart
             :name "Cart"
             :icon "czi-cart"
             :view :psonia.cart/view}
            {:id   :shipping-method
             :name "Delivery"
             :icon "czi-package"
             :view :psonia.shipping.method/view}
            {:id   :shipping-address
             :name "Address"
             :icon "czi-package"
             :view :psonia.shipping.address/view}
            {:id   :payment-method
             :name "Payment"
             :icon "czi-money-bag"
             :view :psonia.payment.method/view}])

(defn step
  [{:keys [name icon count]}]
  [:div.step-progress
   [:span.step-count
    (str count)]])

(defn step-progress
  "Show where in the process the user currently is."
  [steps]
  (fn [{:keys [current-step]
       :or   {current-step (-> (first steps)
                               :id)}}]
    (let [step-ids         (map :id steps)
          idx-current-step (.indexOf step-ids current-step)]
      [:div.steps.steps-light.pt-2.pb-3.mb-5
       (doall
         (for [[idx s] (map-indexed vector steps)]
           ^{:key idx}
           [:a.step-item
            {:class [(when (<= idx idx-current-step)
                       "active")
                     (when (= idx idx-current-step)
                       "current")]
             :href  (when (<= idx idx-current-step)
                      (resolve-href (s :view) {} {}))}
            [step {:name  (s :name)
                   :icon  (s :icon)
                   :count (inc idx)}]
            [:div.step-label
             [:i {:class [(s :icon)]}]
             (s :name)]]))])))

(def checkout-step-progress (step-progress steps))
