(ns psonia.panels.users.core
  (:require [psonia.panels.components :refer [multi-level-navbar]]
            [psonia.panels.users.forms :as forms]))

(defn ^{:page-title "Login"} login
  []
  [:<>
   [multi-level-navbar]
   [:div.container.py-4.py-lg-5.my-4
    [:div.row
     [:div.col-md-6
      [:div.card.border-0.shadow
       [:div.card-body
        [:h2.h4.mb-1 "Sign in"]
        [:hr.my-3]
        [forms/login]]]]
     [:div.col-md-6
      [:h2.h4.mb-3
       "No account? Sign up"]
      [:p.text-muted.font-size-sm.mb-4
       "Registration takes less than a minute but gives you full control over your orders."]
      [forms/register]]]]])

(comment ["/users"
          ["/login"
           {:name :app.users/login}]
          ["/register"
           {:name :app.users/onboarding}]
          ["/profile"]])

(def routes
  [""
   ["/login"
    {:name :psonia.users/login
     :view #'login}]])
