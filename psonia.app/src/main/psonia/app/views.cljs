(ns psonia.app.views
  (:require [psonia.app.panels.home :as home]
            [psonia.app.panels.catalog.core :as catalog]
            [re-frame.core :as re-frame]))

(def panels {:home #'catalog/panel})

(re-frame/reg-event-db
 :set-active-panel
 (fn [db [_ panel]]
   (js/console.log "Setting active panel")
   (js/console.log panel)
   (assoc db :active-panel panel)))

(re-frame/reg-sub
 :active-panel
 (fn [db]
   (let [panels          (:panels db)
         active-panel    (:active-panel db)
         active-panel-fn (panels active-panel)]
     active-panel-fn)))

