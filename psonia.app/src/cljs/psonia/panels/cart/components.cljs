(ns psonia.panels.cart.components
  (:require [re-frame.core :as re-frame]
            [psonia.panels.cart.data]
            [psonia.urls :refer [resolve-href]]
            [reagent.ratom :as ratom]))

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
            {:id   :shipping
             :name "Shipping"
             :icon "czi-package"
             :view :psonia.cart/view}
            {:id   :payment
             :name "Payment"
             :icon "czi-card"
             :view :psonia.cart/view}
            {:id   :review
             :name "Review"
             :icon "czi-check-circle"
             :view :psonia.cart/view}])

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
             :href  (resolve-href (s :view) {} {})}
            [step {:name  (s :name)
                   :icon  (s :icon)
                   :count (inc idx)}]
            [:div.step-label
             [:i {:class [(s :icon)]}]
             (s :name)]]))])))

(def checkout-step-progress (step-progress steps))
