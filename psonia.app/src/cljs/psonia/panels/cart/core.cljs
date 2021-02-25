(ns psonia.panels.cart.core
  (:require [re-frame.core :as re-frame]
            [psonia.layouts.site :as site]
            [psonia.urls :refer [resolve-href]]
            [psonia.money :refer [money]]
            [psonia.panels.cart.forms :as forms]
            [psonia.panels.components :refer [multi-level-navbar]]
            [psonia.panels.cart.components :refer [product-quantity
                                                   checkout-step-progress
                                                   address-selector]]))

(defn ^{:page-title "Cart"} view
  "Views cart"
  []
  (let [items     (re-frame/subscribe [:psonia.cart/items])
        sub-total (re-frame/subscribe [:psonia.cart/sub-total])]
    (fn []
      (let [products      (map #(deref (re-frame/subscribe [:psonia.catalog/product (:id %)])) @items)
            product-items (map vector @items products)]
        [:<>
         [multi-level-navbar]
         [site/page-title "Products" [{:name "Home"
                                       :url  "/"
                                       :icon :home}
                                      {:name "Cart"
                                       :url  "/cart"}]]
         [:div.container.pb-5.mb-2.mb-md-4
          [:div.row
           ;; List of items
           [:section.col-lg-8
            [:div.d-flex.justify-content-between.align-items-center.pt-3.pb-2.pb-sm-5.mt-1
             [:h2.h6.text-light.mb-0
              "Cart"]
             [:a.btn.btn-outline-primary.btn-sm.pl-2
              {:href "/products"}
              [:i.czi-arrow-left.mr-2]
              "Continue shopping"]]

            ;; Products
            (when-not (seq product-items)
              [:div.text-center.py-5.border
               [:p "You have no items in your shopping cart."]
               [:a.btn.btn-outline-primary.btn-sm.pl-2.mt-4
                {:href "/products"}
                "Continue shopping"]])

            (doall
              (for [[i p] product-items]
                ^{:key p}
                [:div.d-sm-flex.justify-content-between.align-items-center.my-4.pb-3.border-bottom
                 [:div.media.media-ie-fix.d-block.d-sm-flex.align-items-center.text-center.text-sm-left
                  [:a.d-inline-block.mx-auto.mr-sm-4
                   {:href  (resolve-href :psonia.catalog.products/view {:id (:id p)} {})
                    :style {:width "10rem"}}
                   [:img {:src "https://via.placeholder.com/240x240"}]]
                  [:div.media-body.pt-2
                   [:h3.product-title.font-size-base.mb-2
                    [:a {:href (resolve-href :psonia.catalog.products/view {:id (:id p)} {})}
                     (:name p)]]
                   [:div.font-size-sm
                    [:span.text-muted.mr-2
                     "Seller:"]
                    "Vendor Name"]
                   [:div.class.font-size-lg.text-accent.pt-2
                    [money (:price p)]]]]
                 [:div.pt-2.pt-sm-0.pl-sm-3.mx-auto.mx-sm-0.text-center.text-sm-left
                  {:style {:max-width "9rem"}}
                  [:div.form-group.mb-0
                   [:label.font-weight-medium
                    "Quantity"]
                   [product-quantity (:qty i) p]]
                  [:button.btn.btn-link.px-0.text-danger
                   {:type     "button"
                    :on-click #(re-frame/dispatch [:psonia.cart/remove-item p])}
                   [:i.czi-close-circle.mr-2]
                   "Remove"]]]))]
           ;; Sidebar -- Order Summary
           [:aside.col-lg-4.pt-4.pt-lg-0
            [:div.cz-sidebar-static.rounded-lg.box-shadow-lg.ml-lg-auto
             [:div.text-center.mb-4.pb-3.border-bottom
              [:h2.h6.mb-3.pb-1 "Subtotal"]
              [:h3.h6.mb-3.pb-1
               [money @sub-total]]]
             [:button.btn.btn-primary.btn-shadow.btn-block.mt-4
              {:on-click #(re-frame/dispatch [:psonia.routes/push-state :psonia.shipping.method/view])
               :disabled (not (seq product-items))}
              [:i.czi-card.font-size-lg.mr-2]
              "Proceed to Checkout"]]]]]]))))

(defn shipping-method
  []
  (let [sub-total       (re-frame/subscribe [:psonia.cart/sub-total])
        shipping-method (re-frame/subscribe [:psonia.order.shipping/method])]
    [:<>
     [multi-level-navbar]
     [site/page-title "Checkout" [{:name "Home"
                                   :url  "/"
                                   :icon :home}
                                  {:name "Checkout"
                                   :url  "#"}]]
     [:div.container.pb-5.mb-2.mb-md-4
      [:div.row
       [:section.col-lg-8
        ;; Steps
        [checkout-step-progress {:current-step :shipping-method}]
        [forms/delivery-method
         {:on-submit     #(re-frame/dispatch [:psonia.order/update-shipping-method %])
          :initial-value @shipping-method}]]
       [:aside.col-lg-4.pt-4.pt-lg-0
        [:div.cz-sidebar-static.rounded-lg.box-shadow-lg.ml-lg-auto
         [:div.text-center.mb-4.pb-3.border-bottom
          [:h2.h6.mb-3.pb-1 "Subtotal"]
          [:h3.h6.mb-3.pb-1
           [money @sub-total]]]
         [:button.btn.btn-primary.btn-shadow.btn-block.mt-4
          {:disabled (not (boolean @shipping-method))
           :on-click #(re-frame/dispatch [:psonia.routes/push-state :psonia.shipping.address/view])}
          [:i.czi-card.font-size-lg.mr-2]
          "Continue"]]]]]]))

(defn shipping-address
  []
  (let [sub-total (re-frame/subscribe [:psonia.cart/sub-total])]
    [:<>
     [multi-level-navbar]
     [site/page-title "Checkout" [{:name "Home"
                                   :url  "/"
                                   :icon :home}
                                  {:name "Checkout"
                                   :url  "#"}]]
     [:div.container.pb-5.mb-2.mb-md-4
      [:div.row
       [:section.col-lg-8
        ;; Steps
        [checkout-step-progress {:current-step :shipping-address}]
        [address-selector]]
       [:aside.col-lg-4.pt-4.pt-lg-0
        [:div.cz-sidebar-static.rounded-lg.box-shadow-lg.ml-lg-auto
         [:div.text-center.mb-4.pb-3.border-bottom
          [:h2.h6.mb-3.pb-1 "Subtotal"]
          [:h3.h6.mb-3.pb-1
           [money @sub-total]]]
         [:a.btn.btn-primary.btn-shadow.btn-block.mt-4
          {:href     (resolve-href :psonia.shipping.address/view {} {})
           :disabled true}
          [:i.czi-card.font-size-lg.mr-2]
          "Continue"]]]]]]))

(defn payment
  []
  [:<>
   [multi-level-navbar]
   [site/page-title "Checkout" [{:name "Home"
                                 :url  "/"
                                 :icon :home}
                                {:name "Checkout"
                                 :url  "#"}]]
   [:div.container.pb-5.mb-2.mb-md-4
    [:div.row
     [:section.col-lg-8
      [checkout-step-progress {:current-step :payment-method}]
      [:h2.h6.pb-3.mb-2
       "Choose payment method"]
      [:div
       [forms/card]]]]]])

(def routes
  [""
   ["/cart"
    {:name :psonia.cart/view
     :view #'view}]
   ["/shipping"
    {:name :psonia.shipping.method/view
     :view #'shipping-method}]
   ["/shipping/address"
    {:name :psonia.shipping.address/view
     :view #'shipping-address}]
   ["/payment"
    {:name :psonia.payment.method/view}]
   ["/payment/details"
    {:name :psonia.payment.card-details/view
     :view #'payment}]])
