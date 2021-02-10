(ns psonia.panels.forms)

(defn make-error-renderer
  [touched errors]
  (fn [control]
    (when (and (touched control) (get errors control))
      [:div [:small.text-danger (get errors control)]])))
