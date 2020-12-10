(ns psonia.app.panels.components
  (:require [psonia.app.layouts.site :as site]
            [reagent.ratom :as ratom]))

(defn multi-level-navbar []
  [:header.box-shadow-sm
   [:div.navbar-sticky.bg-light
    [:div.navbar.navbar-expand-lg.navbar-light
     [:div.container
      [site/brand "Super Street Market"]
      [:div.input-group-overlay.d-none.d-lg-flex.mx-4
       [:input.form-control.appended-form-control
        {:placeholder "Search for products", :type "text"}]
       [:div.input-group-append-overlay
        [:span.input-group-text [:i.czi-search]]]]]]

    [:div.navbar.navbar-expand-lg.navbar-light.navbar-stuck-menu.mt-n2.pt0.pb-2
     [:div.container
      ]]]])

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
