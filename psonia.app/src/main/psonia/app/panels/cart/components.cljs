(ns psonia.app.panels.cart.components
  (:require [re-frame.core :as re-frame]
            [reagent.ratom :as ratom]
            [psonia.app.panels.cart.data :as data]))

(def qty-units {:rate "hr"})

(defn- quantity
  "Gives quantity based on quantity type."
  [{:keys [on-change type end]}]
  (let []
    (fn [options value]
      [:select.custom-select.mr-3
       {:on-change #(on-change (-> (.-target %)
                                   (.-value)
                                   int))}
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
                   :top ["col-12"]
                   :side ["col-sm-12" "col-md-3"]
                   ["col-sm-12" "col-md-3"])}
         [quantity
          {:type (:quantity product)
           :end  5
           :on-change update-value}
          qty]]
        [:div
         {:class (case (:qty-position options)
                   :top ["col-12"]
                   :side ["col-sm-12" "col-md-9"]
                   ["col-sm-12" "col-md-9"])}
         [:button.btn.btn-primary.btn-sm.mt-1
          {:class (or (:button-classes options) [])
           :on-click #(re-frame/dispatch [:psonia.cart/add-to-cart @qty product])}
          "Add to Cart"]]])))
  ([product]
   (add-to-cart-btn {} product)))

(defn product-quantity
  "Updates the amount of products that are in the cart."
  [product]
  [quantity {:on-change #(re-frame/dispatch [:psonia.cart/update-product-qty % product])
             :end       5
             :type      (:quantity product)}])
