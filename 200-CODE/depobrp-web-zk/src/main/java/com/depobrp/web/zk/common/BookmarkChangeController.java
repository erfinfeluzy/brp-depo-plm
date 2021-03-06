package com.depobrp.web.zk.common;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.BookmarkEvent;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Include;

import com.depobrp.model.user.Page;

public class BookmarkChangeController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;

	
	SidebarPageConfig pageConfig = new SidebarPageConfigAjaxBasedImpl();
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		if (comp.getParent() != null) {
			throw new RuntimeException("A bookmark change listener can only apply on the root comp");
		}

		comp.addEventListener("onBookmarkChange", new SerializableEventListener<BookmarkEvent>() {
			private static final long serialVersionUID = 1L;

			public void onEvent(BookmarkEvent event) throws Exception {
				String bookmark = event.getBookmark();

				if(bookmark.startsWith(Context.BOOKMARK_SEPARATOR)){
					String p = bookmark.substring(Context.BOOKMARK_SEPARATOR.length());
					Page page = pageConfig.getPage(p);
					
					if(page!=null){
						//use iterable to find the first include only
						Include include = (Include)Selectors.iterable(getPage(), "#mainInclude").iterator().next();
						include.setSrc(page.getUri());
						Sessions.getCurrent().setAttribute("CURRENT_PAGE", page.getName());
					}
				}
			}
		});
	}
}
