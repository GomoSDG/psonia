(ns psonia.app.routes
    (:require [re-frame.core :as re-frame]
              [reitit.frontend.easy :as rfe]
              [reitit.frontend :as rf]
              [reitit.frontend.controllers :as rfc]
              [reitit.coercion.spec :as rss]
              [psonia.app.panels.catalog.core :as catalog]
              [psonia.app.panels.home :as home]
              [psonia.app.panels.product.core :as product]
              [psonia.app.panels.admin.vendors.core :as vendors]))

(def routes [""
             ["/"
              {:name :app/home
               :view #'home/panel}]
             ;; products
             ["/products"
              [""
               {:name :app/products
                :view #'catalog/panel}]
              ["/:id"
               {:name :app.products/view
                :view #'product/panel}]]

             ;; admin
             ["/admin"
              ;; admin vendors
              ["/vendors"
               [""
                {:name :app.admin.vendors/list
                 :view #'vendors/list-all}]
               ["/:vendor-id"
                {:name :app.admin.vendors/view
                 :view #'vendors/vendor-view}]
               ["/vendor-id/products"
                {:name :app.admin.vendors/products}]

               ;; admin products
               ]]

             ;; users
             ["/users"
              ["/login"
               {:name :app.users/login}]
              ["/onboarding"
               {:name :app.users/onboarding}]
              ["/profile"]]

             ;; cart
             ["cart"
              [""
               {:name :app.cart/view}]]

             ;; checkout
             ["/checkout"
              {:name :app.cart/checkout}]

             ;; orders
             ["/orders"]

             ;; wishlist
             ["/wishlist"]])

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
