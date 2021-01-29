(ns psonia.app.panels.cart.core
  (:require [re-frame.core :as re-frame]
            [psonia.app.layouts.site :as site]
            [psonia.app.urls :refer [resolve-href]]
            [psonia.app.money :refer [money]]
            [psonia.app.panels.components :refer [multi-level-navbar]]
            [psonia.app.panels.cart.components :refer [product-quantity]]))

(defn view
  "Views cart"
  []
  (let [items (re-frame/subscribe [:psonia.cart/items])]
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
                  "Remove"]]]))]]]]))))

(def routes
  ["/cart"
   [""
    {:name :psonia.cart/view
     :view #'view}]])
