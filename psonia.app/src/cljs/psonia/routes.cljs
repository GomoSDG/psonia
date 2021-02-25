(ns psonia.routes
  (:require [re-frame.core :as re-frame]
            [reitit.frontend.easy :as rfe]
            [reitit.frontend :as rf]
            [reitit.frontend.controllers :as rfc]
            [reitit.coercion.spec :as rss]
            [psonia.panels.catalog.core :as catalog]
            [psonia.panels.cart.core :as cart]
            [psonia.panels.users.core :as users]
            [psonia.panels.home :as home]
            [psonia.panels.core :as panels]
            [psonia.panels.admin.routes :as admin]))

(def routes [""
             ["/"
              {:name :psonia/home
               :view #'home/panel}]
             ;; products
             catalog/routes

             ;; users
             users/routes

             ;; cart
             cart/routes

             ;; checkout
             ["/checkout"
              {:name :app.cart/checkout}]

             ;; orders
             ["/orders"]

             ;; wishlist
             ["/wishlist"]

             ;; admin
             admin/routes])

(defn router-component [_]
  (let [current-route @(re-frame/subscribe [:routes/current-route])
        view          (-> current-route :data :view)
        params        (:parameters current-route)
        title         (panels/page-title view)]
    (set! (.-title js/document) (str title " - Super Street"))
    [:div
     (when current-route
       [view (reduce merge (vals params))])]))

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

(re-frame/reg-event-db
  :routes/navigated
  (fn [db [_ new-match]]
    (let [old-match   (:current-route db)
          controllers (rfc/apply-controllers (:controllers old-match) new-match)]
      (assoc db :current-route (assoc new-match :controllers controllers)))))

(re-frame/reg-event-fx
  :psonia.routes/push-state
  (fn [_ [_ & route]]
    {:push-state route}))

;; Subcsriptions

(re-frame/reg-sub :routes/current-route
                  (fn [db]
                    (:current-route db)))

;; Effects
(re-frame/reg-fx :push-state
                 (fn [route]
                   (apply rfe/push-state route)))
