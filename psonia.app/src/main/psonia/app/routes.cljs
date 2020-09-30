(ns psonia.app.routes
    (:require [secretary.core :as secretary :refer-macros [defroute]]
              [re-frame.core :as re-frame]
              [goog.events :as events]
              [goog.history.EventType :as HistoryEventType])
    (:import goog.History))

(secretary/set-config! :prefix "#")

(defroute home "/" []
  (re-frame/dispatch [:set-active-panel :home]))

(defroute admin-users "/admin/vendors" []
  (re-frame/dispatch [:set-active-panel :admin/vendors]))

(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
     HistoryEventType/NAVIGATE
     (fn [event] 
       (secretary/dispatch! ^String (.-token event))))
    (.setEnabled true)))
