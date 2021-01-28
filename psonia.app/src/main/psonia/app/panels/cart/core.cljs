(ns psonia.app.panels.cart.core
  (:require [re-frame.core :as re-frame]
            [psonia.app.panels.components :refer [multi-level-navbar]]))

(defn view
  "Views cart"
  []
  [:<>
   [multi-level-navbar]])

(def routes
  ["/cart"
   [""
    {:name :psonia.cart/view
     :view #'view}]])
