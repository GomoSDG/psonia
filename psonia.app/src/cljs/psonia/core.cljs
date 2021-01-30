(ns psonia.core
  (:require [reagent.dom :as rdom]
            [clojure.set :as set]
            [psonia.routes :refer [init-routes! router-component] :as routes]
            [re-frame.core :as re-frame]
            [cljs.spec.gen.alpha :as gen]
            [psonia.entities.products :as products]
            [psonia.entities.vendors :as vendors]
            [psonia.entities.address :as address]
            [cljs.spec.alpha :as s]
            [clojure.test.check]
            [clojure.test.check.properties]))

(re-frame/reg-event-db
  :initialize-db
  (fn [_ _]
    {:active-panel          :home
     :products              (set/index (gen/sample (s/gen ::products/spec) 5) [:id])
     :vendors               (set/index (gen/sample (s/gen ::vendors/spec) 5) [:id])
     :psonia.cart/addresses (set/index (gen/sample (s/gen ::address/spec) 3) [:id])}))

(defn render-active-panel []
  (re-frame/clear-subscription-cache!)
  (rdom/render [router-component {:router routes/router}]
               (.getElementById js/document "app")))

(defn init []
  (re-frame/clear-subscription-cache!)
  (re-frame/dispatch-sync [:initialize-db])
  (init-routes!) ;; Reset routes on figwheel reload
  (render-active-panel))
