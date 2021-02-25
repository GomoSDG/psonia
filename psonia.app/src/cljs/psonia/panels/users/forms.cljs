(ns psonia.panels.users.forms
  (:require [fork.re-frame :as fork]))

(defn login
  [{:keys [on-submit]}]
  [fork/form
   {:on-submit        (comp on-submit :values)
    :keywordize-keys  true
    :prevent-default? true}
   (fn [{:keys
        [values
         handle-change
         handle-submit]}]
     [:div
      [:div.input-group-overlay.form-group
       [:div.input-group-prepend-overlay
        [:span.input-group-text
         [:i.czi-mail]]]
       [:input.form-control.prepended-form-control
        {:type        "email"
         :placeholder "Email"}]]
      [:div.input-group-overlay.form-group.mb-3
       [:div.input-group-prepend-overlay
        [:span.input-group-text
         [:i.czi-locked]]]
       [:input.form-control.prepended-form-control
        {:type        "password"
         :placeholder "Password"}]]
      [:div.d-flex.flex-wrap.justify-content-between
       [:div.custom-control.custom-checkbox
        [:input#remember-me.custom-control-input
         {:type "checkbox"}]
        [:label.custom-control-label
         {:for "remember-me"}
         "Remember me"]]
       [:a.nav-link-inline.fs-sm
        {:href "#"}
        "Forgot password?"]]
      [:div.text-right.pt-4
       [:button.btn.btn-primary
        {:type "submit"}
        [:i.czi-sign-in.mr-2.ml-n21]
        "Sign In"]]])])

(defn register
  [{:keys [on-submit]}]
  [fork/form
   {:on-submit        (comp on-submit :values)
    :keywordize-keys  true
    :prevent-default? true}
   (fn [{:keys
        [values
         handle-change
         handle-submit]}]
     [:div
      [:div.row
       [:div.col-sm-6
        [:div.form-group
         [:label
          {:for "reg-fn"}
          "First Name"]
         [:input#reg-fn.form-control
          {:type "text"}]
         [:div.invalid-feedback "Please enter your first name"]]
        [:div.col-sm-6
         [:div.form-group
          [:label
           {:for "reg-ln"}
           "Last Name"]
          [:input#reg-ln.form-control
           {:type "text"}]
          [:div.invalid-feedback "Please enter your last name"]]]]]])])
