(ns psonia.app.panels.cart.components
  (:require [re-frame.core :as re-frame]
            [reagent.ratom :as ratom]))

(def qty-units {:rate "hr"})

(defn- quantity
  "Gives quantity based on quantity type."
  [options value]
  (let []
    (fn [options value]
      [:select.custom-select.mr-3
       [:option {:value 1}
        (str 1 " " (qty-units (:type options)))]
       [:option {:value 2}
        (str 2 " " (qty-units (:type options)))]
       [:option {:value 3}
        (str 3 " " (qty-units (:type options)))]])))

(defn add-to-cart-btn
  "Adds products/services to cart"
  ([options product]
   (let [qty (ratom/atom 1)]
     (fn [options product]
       [:div.form-group.row
        [:div
         {:class (case (:qty-position options)
                   :top ["col-12"]
                   :side ["col-3"]
                   ["col-3"])}
         [quantity
          {:type (:quantity product)}
          qty]]
        [:div
         {:class (case (:qty-position options)
                   :top ["col-12"]
                   :side ["col-9"]
                   ["col-9"])}
         [:button.btn.btn-primary.btn-sm.mt-1
          {:class (or (:button-classes options) [])
           :on-click #(re-frame/dispatch [:app.cart/add-to-cart quantity product])}
          "Add to Cart"]]])))
  ([product]
   (add-to-cart-btn {} product)))
