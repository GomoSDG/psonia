(ns psonia.app.panels.admin.routes
  (:require [psonia.app.panels.admin.vendors.core :as vendors]))

(def routes
  ["/admin"
   ;; admin vendors
   vendors/routes])
