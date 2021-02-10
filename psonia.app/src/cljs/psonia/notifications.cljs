(ns psonia.notifications
  (:require [re-frame.core :as rf]
            ["jquery" :as $]))

(defn toast-options
  [alert-type]
  (cond->
      {}
    (= alert-type :primary)
    (assoc :header-class "bg-primary"
           :text-class   "text-primary")


    (= alert-type :accent)
    (assoc :header-class "bg-accent"
           :text-class   "text-accent"
           :icon         "czi-unlocked")

    (= alert-type :success)
    (assoc :header-class "bg-success"
           :text-class   "text-success"
           :icon         "czi-check-circle")

    (= alert-type :danger)
    (assoc :header-class "bg-danger"
           :text-class   "text-danger"
           :icon         "czi-close-circle")

    (= alert-type :info)
    (assoc :header-class "bg-info"
           :text-class   "text-info"
           :icon         "czi-announcement")

    (= alert-type :warning)
    (assoc :header-class "bg-warning"
           :text-class   "text-warning"
           :icon         "czi-security-announcement")))

(defn init-toast
  [el]
  (let [j-el ($ el)]
    (.toast j-el #js {"animation" true
                      "autohide"  false
                      "delay"     2000})
    (.toast j-el "show")))

(defn toast [{:keys [alert-type position header body]
              :or   {position :bottom-right}}]
  (let [{:keys                                                                [header-class
                                                                        text-class
                                                                        icon] :as o} (toast-options alert-type)]
    (js/console.log "ALERT TYPE: " alert-type o)
    [:div.toast-container
     {:class [(str "toast-" (name position))]}
     [:div.toast
      {:role        "alert"
       :aria-live   "assertive"
       :aria-atomic "true"
       :ref         init-toast}
      [:div.toast-header.text-white
       {:class [header-class]}
       [:i.mr-2 {:class [icon]}]
       [:span.font-weight-medium.mr-auto
        header]
       [:button.close.text-white.ml-2.mb-1
        {:type         "button"
         :data-dismiss "toast"
         :aria-label   "close"}
        [:span
         {:aria-hidden             "true"
          :dangerouslySetInnerHTML {:__html "&times;"}}]]]
      [:div.toast-body
       {:class [text-class]}
       body]]]))
