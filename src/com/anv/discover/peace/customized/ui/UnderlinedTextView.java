package com.anv.discover.peace.customized.ui;

import android.content.Context;
import android.text.SpannableString;

import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.widget.TextView;

public class UnderlinedTextView extends TextView{

	public UnderlinedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public void setText(CharSequence text, BufferType type) {
	    // code to check text for null omitted
	    SpannableString content = new SpannableString(text);
	    content.setSpan(new UnderlineSpan(), 0, text.length(), 0);
	    super.setText(content, BufferType.SPANNABLE);

	}

	

}
