(ns psonia.app.panels.cart.data
  (:require [re-frame.core :as re-frame]))

(defn add-to-cart!
  "Adds a product or service to cart."
  [product]
  (re-frame/dispatch [:app.cart/add-to-cart product]))


