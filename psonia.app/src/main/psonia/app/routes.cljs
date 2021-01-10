(ns psonia.app.routes
    (:require [re-frame.core :as re-frame]
              [reitit.frontend.easy :as rfe]
              [reitit.frontend :as rf]
              [reitit.frontend.controllers :as rfc]
              [reitit.coercion.spec :as rss]
              [psonia.app.panels.catalog.core :as catalog]
              [psonia.app.panels.home :as home]
              [psonia.app.panels.admin.vendors.core :as vendors]))

(def routes ["/"
             [""
              {:name :routes/home
               :view #'home/panel}]
             ["products"
              {:name :routes/products
               :view #'catalog/panel}]
             ["admin/"
              ["vendors"
               {:name :admin.vendors
                :view #'vendors/list-all}]
              ["vendors/"
               [":id/view"
                {:name :admin.vendors.view
                 :view #'vendors/vendor-view}]]]])

(defn router-component [{:keys [router]}]
  (let [current-route @(re-frame/subscribe [:routes/current-route])]
    [:div
     (when current-route
       [(-> current-route :data :view)])]))

(def router
  (rf/router
   routes
   {:data {:coercion rss/coercion}}))

(defn on-navigate [new-match]
  (when new-match
    (re-frame/dispatch [:routes/navigated new-match])))

(defn init-routes! []
  (rfe/start!
   router
   on-navigate
   {:use-fragment false}))

;; Events

(re-frame/reg-event-fx :routes/push-state
                       (fn [db [_ & route]]
                         {:push-state route}))

(re-frame/reg-event-db :routes/navigated
                       (fn [db [_ new-match]]
                         (let [old-match   (:current-route db)
                               controllers (rfc/apply-controllers (:controllers old-match) new-match)]
                           (assoc db :current-route (assoc new-match :controllers controllers)))))

;; Subcsriptions

(re-frame/reg-sub :routes/current-route
                  (fn [db]
                    (:current-route db)))

;; Effects
(re-frame/reg-fx :push-state
                 (fn [route]
                   (apply rfe/push-state route)))
