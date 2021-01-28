(ns psonia.app.core
    (:require [reagent.core :as r]
              [reagent.dom :as rdom]
              [psonia.app.layouts.site :refer [site-layout]]
              [psonia.app.routes :refer [init-routes! router-component] :as routes]
              [re-frame.core :as re-frame]
              [cljs.spec.gen.alpha :as gen]
              [psonia.app.entities.products :as products]
              [psonia.app.entities.vendors :as vendors]
              [cljs.spec.alpha :as s]
              [clojure.test.check]
              [clojure.test.check.properties]))

(re-frame/reg-event-db
 :initialize-db
 (fn [_ _]
   {:active-panel :home
    :products (clojure.set/index (gen/sample (s/gen ::products/spec) 5) [:id])
    :vendors (clojure.set/index (gen/sample (s/gen ::vendors/spec) 5) [:id])}))

(defn render-active-panel []
  (re-frame/clear-subscription-cache!)
  (rdom/render [router-component {:router routes/router}]
            (.getElementById js/document "app")))

(defn init []
  (re-frame/clear-subscription-cache!)
  (re-frame/dispatch-sync [:initialize-db])
  (init-routes!) ;; Reset routes on figwheel reload
  (render-active-panel))
