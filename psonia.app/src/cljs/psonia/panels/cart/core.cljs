(ns psonia.panels.cart.core
  (:require [re-frame.core :as re-frame]
            [psonia.layouts.site :as site]
            [psonia.urls :refer [resolve-href]]
            [psonia.money :refer [money]]
            [psonia.panels.components :refer [multi-level-navbar]]
            [psonia.panels.cart.components :refer [product-quantity checkout-step-progress]]))

(defn view
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
              {:href "#"}
              [:i.czi-arrow-left.mr-2]
              "Continue shopping"]]

            ;; Products
            (doall
              (for [[i p] product-items]
                ^{:key p}
                [:div.d-sm-flex.justify-content-between.align-items-center.my-4.pb-3.border-bottom
                 [:div.media.media-ie-fix.d-block.d-sm-flex.align-items-center.text-center.text-sm-left
                  [:a.d-inline-block.mx-auto.mr-sm-4
                   {:href  (resolve-href :app.products/view {:id (:id p)} {})
                    :style {:width "10rem"}}
                   [:img {:src "https://via.placeholder.com/240x240"}]]
                  [:div.media-body.pt-2
                   [:h3.product-title.font-size-base.mb-2
                    [:a {:href (resolve-href :app.products/view {:id (:id p)} {})}
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
           ;; Sidebar
           [:aside.col-lg-4.pt-4.pt-lg-0
            [:div.cz-sidebar-static.rounded-lg.box-shadow-lg.ml-lg-auto
             [:div.text-center.mb-4.pb-3.border-bottom
              [:h2.h6.mb-3.pb-1 "Subtotal"]
              [:h3.h6.mb-3.pb-1
               [money @sub-total]]]
             [:a.btn.btn-primary.btn-shadow.btn-block.mt-4
              [:i.czi-card.font-size-lg.mr-2]
              "Proceed to Checkout"]]]]]]))))

(defn shipping
  [{:keys [address]}]
  (let [addresses        (re-frame/subscribe [:psonia.cart/addresses])
        first-address-id (-> (keys @addresses)
                             first)
        selected-address (or address first-address-id)]
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
        [checkout-step-progress {:current-step :shipping}]
        [:h2.h6.pb-3.mb-2
         "Choose shipping address"]
        [:div.table-responsive
         [:table.table.table-hover.font-size-sm.border-bottom
          [:thead
           [:tr
            [:th.align-middle]
            [:th.align-middle "Address"]]]
          [:tbody
           (doall
             (for [[adr-id adr] (map vector (keys @addresses) (vals @addresses))]
               [:tr
                [:td
                 [:div.custom-control.custom-radio.mb-4
                  [:input.custom-control-input
                   {:type "radio"
                    :id   (str adr-id)
                    :name "shipping-address"}]
                  [:label.custom-control-label
                   {:for (str adr-id)}]]]
                [:td
                 (str (apply str (interpose ", " (vals adr))))]]))]]]]]]]))

(defn payment
  [])

(def routes
  [""
   ["/cart"
    {:name :psonia.cart/view
     :view #'view}]
   ["/shipping"
    {:name :psonia.shipping/view
     :view #'shipping}]
   ["/payment"
    {:name :psonia.payment/view
     :view #'payment}]])