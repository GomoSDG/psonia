(ns psonia.panels.cart.forms
  (:require [fork.re-frame :as fork]
            [psonia.models.credit-card :as cc]
            [clojure.string :as str]
            [psonia.models.shipping :as shipping]
            [psonia.panels.forms :refer (make-error-renderer)]
            [psonia.notifications :as notifications]))

;;;;;;;;;;;;;;;;;;;;;;; DELIVERY METHOD FORM ;;;;;;;;;;;;;;;;;;

(defn delivery-method
  [{:keys [on-submit
           initial-value]}]
  [fork/form
   {:on-submit        (comp on-submit keyword :shipping-method :values)
    :initial-values   {:shipping-method (when initial-value
                                          (name initial-value))}
    :keywordize-keys  true
    :prevent-default? true}
   (fn [{:keys
        [values
         handle-change
         handle-submit]}]
     [:<>
      [:h2.h6.pb-3.mb-2
       "Choose shipping method"]
      [:div.table-responsive
       [:table.table.table-hover.font-size-sm.border-bottom
        [:thead
         [:tr
          [:th.align-middle
           "Method"]]]
        [:tbody
         (map (fn [method]
                ^{:key method}
                [:tr
                 [:td.d-flex
                  [:div.custom-control.custom-radio.my-2.d-flex
                   {:style {:width "100%"}}
                   [:input.custom-control-input
                    {:type      "radio"
                     :required  true
                     :id        method
                     :value     method
                     :name      "shipping-method"
                     :checked   (= (name method) (get values :shipping-method))
                     :on-change (fn [e]
                                  (handle-change e)
                                  (when (-> (.-target e)
                                            (.-checked))
                                    (handle-submit e)))}]
                   [:label.custom-control-label.flex-grow-1
                    {:for method}
                    (-> (name method)
                        str/capitalize)]]]])
              shipping/delivery-methods)]]]])])

;;;;;;;;;;;;;;;;;;;;;;; CARD FORM ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn validate-card
  [values]
  (let [cvc         (get values :psonia.credit-card/cvc)
        card-number (get values :psonia.credit-card/number)
        full-name   (get values :psonia.credit-card/full-name)
        expiry      (get values :psonia.credit-card/expiry)]
    (cond->
        {}
      (not (cc/valid-cvc? cvc))
      (assoc :psonia.credit-card/cvc
             "CVC number must be of length 3 to 4.")
      (not (cc/valid-card-number? card-number))
      (assoc :psonia.credit-card/number
             "Invalid credit card number")
      (not (cc/valid-full-name? full-name))
      (assoc :psonia.credit-card/full-name
             "Please enter name.")
      (not (cc/valid-expiry? expiry))
      (assoc :psonia.credit-card/expiry
             "Please enter valid expiry date."))))

(defn card
  [{:keys [on-submit]}]
  [fork/form
   {:validation       validate-card
    :on-submit        on-submit
    :keywordize-keys  true
    :prevent-default? true}
   (fn [{:keys
        [touched
         errors
         handle-change
         handle-blur
         handle-submit]}]
     (let[render-error (make-error-renderer touched errors)]
       [:<>
        [notifications/toast {:alert-type :warning}]
        [:div.card-wrapper]
        [:div.card-data.interactive-credit-card.row
         {:ref       #(when %
                        (js/Card. #js {"form"      "div.card-data"
                                       "container" "div.card-wrapper"
                                       "formSelectors"
                                       #js {"nameInput"   "input[name=\"psonia.credit-card/full-name\"]"
                                            "numberInput" "input[name=\"psonia.credit-card/number\"]"
                                            "cvcInput"    "input[name=\"psonia.credit-card/cvc\"]"
                                            "expiryInput" "input[name=\"psonia.credit-card/expiry\"]"}}))
          :on-submit handle-submit}
         [:div.form-group.col-sm-6
          [:input.form-control
           {:type        "text"
            :name        "psonia.credit-card/number"
            :on-change   handle-change
            :on-blur     handle-blur
            :placeholder "Card Number"}]
          [render-error :psonia.credit-card/number]]
         [:div.form-group.col-sm-6
          [:input.form-control
           {:type        "text"
            :name        "psonia.credit-card/full-name"
            :on-change   handle-change
            :on-blur     handle-blur
            :placeholder "Full Name"}]
          [render-error :psonia.credit-card/full-name]]
         [:div.form-group.col-sm-3
          [:input.form-control
           {:type        "text"
            :name        "psonia.credit-card/expiry"
            :on-change   handle-change
            :on-blur     handle-blur
            :placeholder "MM/YY"}]
          [render-error :psonia.credit-card/expiry]]
         [:div.form-group.col-sm-3
          [:input.form-control
           {:type        "text"
            :name        "psonia.credit-card/cvc"
            :on-change   handle-change
            :on-blur     handle-blur
            :placeholder "CVC"}]
          [render-error :psonia.credit-card/cvc]]
         [:div.form-group.col-sm-6
          [:button.btn.btn-outline-primary.btn-block.mt-0
           {:type "submit"}
           "Add Card"]]]]))])
