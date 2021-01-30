(ns psonia.panels.admin.routes
  (:require [psonia.panels.admin.vendors.core :as vendors]))

(def routes
  ["/admin"
   ;; admin vendors
   vendors/routes])
