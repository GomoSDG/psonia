(ns psonia.app.panels.components
  (:require [psonia.app.layouts.site :as site]
            [reagent.ratom :as ratom]
            [re-frame.core :as re-frame]
            [psonia.app.money :refer [money]]
            [psonia.app.urls :refer [resolve-href]]
            [jquery :as $ ]))

(defn multi-level-navbar []
  (let [items-in-cart    (re-frame/subscribe [:psonia.cart/item-count])
        cart-total-price (re-frame/subscribe [:psonia.cart/total-price])]
    (fn []
      (js/console.log "CART PRICE" @cart-total-price)
      [:header.box-shadow-sm
       ;; Top layer can be added here.

       ;; Search and Login Layer
       [:div.navbar.navbar-expand-lg.navbar-light.bg-light
        [:div.container
         [site/brand "Super Street Market"]
         ;; Search widget
         [:div.input-group-overlay.d-none.d-lg-flex.mx-4
          [:input.form-control.appended-form-control
           {:placeholder "Search for products", :type "text"}]
          [:div.input-group-append-overlay
           [:span.input-group-text [:i.czi-search]]]]

         ;; navbar toolbar widget
         [:div.navbar-toolbar.d-flex.align-items-center
          ;; collapse button
          [:button.navbar-toggler
           {:type "button"
            :data-toggle "collapse"
            :data-target "#navbarCollapse"}
           [:span.navbar-toggler-icon]]

          ;; search button (Catalog)
          [:a.navbar-tool.d-lg-none
           {:href "#"}
           [:div.navbar-icon-box
            [:i.navbar-tool-icon.czi-search]]]
          ;; Profile Button (User)

          [:a.navbar-tool.ml-1.ml-lg-0.mr-n1.mr-lg-2
           {:href "#"}
           [:div.navbar-tool-icon-box
            [:i.navbar-tool-icon.czi-user]]
           [:div.navbar-tool-text.ml-2
            [:small "Hello, Sign in"]
            "My Account"]]

          ;; Cart Button (Cart)
          [:a.navbar-tool.ml-3
           {:href "#"}
           [:div.navbar-tool-icon-box.bg-secondary
            (when-not (zero? @items-in-cart)
                [:span.navbar-tool-label (str @items-in-cart)])
            [:i.navbar-tool-icon.czi-cart]]
           [:div.navbar-tool-text
            [:small "My Cart"]
            [money @cart-total-price]]]]]]

       ;; Second layer
       [:div.navbar.navbar-expand-lg.navbar-light.bg-light.mt-n2.pt-0.pb-2
        [:div.container
         [:hr.d-lg-none.mt-3.mb-2]
         [:div#navbarCollapse.collapse.navbar-collapse[:ul.navbar-nav
                                                       [:li.nav-item
                                                        [:a.nav-link
                                                         {:href (resolve-href :app/home {:id 1} {})}
                                                         "Home"]]
                                                       [:li.nav-item
                                                        [:a.nav-link
                                                         {:href "#"}
                                                         "Services"]]
                                                       [:li.nav-item
                                                        [:a.nav-link
                                                         {:href "#"}
                                                         "Products"]]]]]]])))

;; Sidebar
(defn sidebar-items [items]
  [:ul.list-unstyled.mb-0
   (for [i items]
     (do (js/console.log i)
         (let [{icon   :icon
                name   :name
                href   :href
                active :active} i]
           [:li.border-bottom.mb-0
            [:a.nav-link-style.d-flex.align-items-center.px-4.py-3
             {:class (when active ["active"])
              :href  href}
             (when (seq icon)
               [:i.opacity-60.mr-2 {:class [icon]}])
             name]])))])

(defn sidebar-section [{:keys [name items]}]
  [:<>
   [:div.bg-secondary.p-4
    [:h3.font-size-sm.mb-0.text-muted
     name]]
   [sidebar-items items]])

(defn static-sidebar
  ([sections]
   (static-sidebar nil sections))
  ([options sections]
   (let [[menu-name] options]
     [:aside.col-lg-4
      [:div.d-block.d-lg-none.p-4
       [:a.btn.btn-outline-accent.d-block {:href        "#sidebar"
                                           :data-toggle "collapse"}
        [:i.czi-menu.mr-2]
        menu-name]]
      [:div.cz-sidebar-static.h-100.p-0
       [:div#sidebar.secondary-nav.collapse.border-right
        (for [s sections]
          [sidebar-section s])]]])))

(comment [{:body fn
           :display-name "General"
           :id :general}
          {:body fn
           :icon ""
           :display-name "Contact Details"
           :id :contact-details}])

(comment [])

(defn tab [id name body]
  (js/console.log "Body" (if (fn? body)
                           body
                           (fn [] body))
                  body)
  {:body (if (fn? body)
           body
           (fn [] body))
   :id id
   :display-name name})

(defn tablist
  ([options tablist]
   (let [cnt (ratom/atom 0)
         tabs (clojure.set/index tablist [:id])
         current-tab (ratom/atom (:id (first tablist)))]
     (fn []
       [:<>
        [:ul.nav.nav-tabs.nav-justified {:role "tablist"}
         (doall
          (for [{:keys [display-name id]} tablist]
            ^{:key id} [:li.nav-item
                        [:a.nav-link.px-0  {:href (str "#" id)
                                            :class (if (= @current-tab id) ["active"])
                                            :on-click #(reset! current-tab id)}
                         [:div.d-none.d-lg-block {:data-toggle "tab"
                                                  :role "tab"}
                          display-name]
                         [:div.d-lg-none.text-center {:data-toggle "tab"
                                                      :role "tab"}
                          display-name]]]))]
        [:div.tab-content
         (doall
          (for [{:keys [id body]} tablist]
            ^{:key (str id "body")} [:div.tab-pane.fade {:id id
                                                         :role "tabpanel"
                                                         :class (if (= @current-tab id) ["show" "active"])}
                                     [body]]))]])))
  ([t]
   (tablist nil t)))

;; File Uploader Component

(defn file-drop-handler [reader]
  (defn handle-file-drop [ev]
    (let [files (.-items (.-dataTransfer ev))]
      ;; prevent default behavior
      (.preventDefault ev)
      (doseq [i (range (.-length files))]
        (let [file (aget files i)]
          (js/console.log (.readAsDataURL reader (.getAsFile file))))))))

(defn file-uploader [reset-file!]
  (let [reader  (js/FileReader.)]
    (.addEventListener reader "load" #(-> (.-result reader)
                                          reset-file!))
    (fn []
      [:div.cz-file-drop-area {:on-drop      (file-drop-handler reader)
                               :on-drag-over #(.preventDefault %)}
       [:div.cz-file-drop-icon.czi-cloud-upload]
       [:span.cz-file-drop-message "Drag and drop here to upload."]
       [:input#file-uploader.cz-file-drop-input {:type     "file"
                                   :multiple false
                                   :accept   "image/*"}]
       [:label.cz-file-drop-btn.btn.btn-primary.btn-sm {:type "button"
                                                        :for "file-uploader"}
        "Or select file"]])))

;; dropdown
(defn toggle-menu-items [e]
  (.preventDefault e)
  (.stopPropagation e)

  (this-as this
    (let [el ($ this)]
      (-> (.siblings el)
          (.toggleClass "show"))

      (if (not (-> (.next el)
                   (.hasClass "show")))
        (-> (.parents el ".dropdown-menu")
            (.first)
            (.find "show")
            (.removeClass "show")))

      (-> (.parents el "li.nav-item.dropdown.show")
          (.on "hidden.bs.dropdown", (fn []
                                       (-> ($ ".dropdown-submenu .show")
                                           (.removeClass "show"))))))))

(defn init-dropdown [^js el]
  (when el
    (-> ($ el)
        (.on "click" toggle-menu-items))))
