package com.devspark.collapsiblesearchmenu;

import android.content.Context;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnActionExpandListener;

/**
 * 
 * @author e.shishkin
 *
 */
public class AddFriendMenu {
	
	/**
	 * Adding collapsible  menu item to the menu.
	 * @param menu
	 * @return
	 */
	public static AutoCompleteTextView editText;
	public static MenuItem menuItem;
	public static MenuItem addSearchMenuItem(Menu menu) {
		menuItem = menu.add(Menu.NONE, R.id.addfriend_search_menu_item, Menu.NONE, R.string.add_friend);
		menuItem.setIcon(R.drawable.ic_menu_invite).setActionView(R.layout.addfriend_view_holo_light)
	        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		final View searchView = menuItem.getActionView();
		editText = (AutoCompleteTextView) searchView.findViewById(R.id.addfriend_src_text);
		menuItem.setOnActionExpandListener(new OnActionExpandListener() {
			
			@Override
			public boolean onMenuItemActionExpand(MenuItem item) {
				NavigateToFriendMenu.menuItem.collapseActionView();
				editText.requestFocus();
				showKeyboard(editText);
				return true;
			}
			
			@Override
			public boolean onMenuItemActionCollapse(MenuItem item) {
				editText.setText(null);
				hideKeyboard(editText);
				return true;
			}
		});
		
		final View searchPlate = searchView.findViewById(R.id.addfriend_plate);
		editText.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, final boolean hasFocus) {
				searchView.post(new Runnable() {
					public void run() {
						searchPlate.getBackground().setState(hasFocus ? 
								new int[] {android.R.attr.state_focused} : new int[] {android.R.attr.state_empty});
						searchView.invalidate();
					}
				});
			}
		});
		
		final ImageView closeBtn = (ImageView) menuItem.getActionView().findViewById(R.id.addfriend_close_btn);
		closeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!TextUtils.isEmpty(editText.getText())) {
					editText.setText(null);
				} else {
					menuItem.collapseActionView();
				}
			}
		});
		
		return menuItem;
	}
	
	/**
	 * Shows the keyboard.
	 * @param view
	 */
	private static void showKeyboard(View view) {
	    Context context = view.getContext();
	    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
	    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	/**
	 * Hides the keyboard.
	 * @param view
	 */
	private static void hideKeyboard(View view) {
	    Context context = view.getContext();
	    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
	    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

}
