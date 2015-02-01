package com.shantanu.eqnsolver;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import de.congrace.exp4j.ExpressionBuilder;

public class Linear extends Activity implements OnClickListener {
	
	EditText et1, et2, et3, et4, et5, et6;
	Button b1;
	TextView tv1, tv2;
	
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		this.setContentView(R.layout.linear);
		et1=(EditText) this.findViewById(R.id.s2_editText1);
		et2=(EditText) this.findViewById(R.id.s2_editText2);
		et3=(EditText) this.findViewById(R.id.s2_editText3);
		et4=(EditText) this.findViewById(R.id.s2_editText4);
		et5=(EditText) this.findViewById(R.id.s2_editText5);
		et6=(EditText) this.findViewById(R.id.s2_editText6);
		tv1=(TextView) this.findViewById(R.id.s2_textView1);
		tv2=(TextView) this.findViewById(R.id.s2_textView2);
		if(icicle!=null){
			tv1.setText(icicle.getString("x"));
			tv2.setText(icicle.getString("y"));
		}
		b1=(Button) this.findViewById(R.id.s2_button1);
		b1.setOnClickListener(this);
		et1.requestFocus();
	}
	
	public void onSaveInstanceState(Bundle save){
		super.onSaveInstanceState(save);
		save.putString("x", tv1.getText().toString());
		save.putString("y", tv2.getText().toString());
	}

	public void onDestroy(){
		super.onDestroy();
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Exception ex=null;
		double a1=0, b1=0, a2=0, b2=0, c1=0, c2=0;
		int i=1;
		try {
			a1=new ExpressionBuilder(et1.getText().toString()).withVariable("pi", Math.PI).withVariable("e", Math.E).build().calculate(); i++;
			b1=new ExpressionBuilder(et2.getText().toString()).withVariable("pi", Math.PI).withVariable("e", Math.E).build().calculate(); i++;
			c1=new ExpressionBuilder(et3.getText().toString()).withVariable("pi", Math.PI).withVariable("e", Math.E).build().calculate(); i++;
			a2=new ExpressionBuilder(et4.getText().toString()).withVariable("pi", Math.PI).withVariable("e", Math.E).build().calculate(); i++;
			b2=new ExpressionBuilder(et5.getText().toString()).withVariable("pi", Math.PI).withVariable("e", Math.E).build().calculate(); i++;
			c2=new ExpressionBuilder(et6.getText().toString()).withVariable("pi", Math.PI).withVariable("e", Math.E).build().calculate(); i++;
		}catch(Exception e){
			ex=e;
		}
		if(ex!=null){
			AlertDialog.Builder di=new AlertDialog.Builder(Linear.this);
			String err=ex.toString().replaceFirst("de.congrace.exp4j.", "");
			switch(i){
			case 1: di.setTitle("Error").setMessage("Field a1:\n"+err).show();
					break;
			case 2: di.setTitle("Error").setMessage("Field b1:\n"+err).show();
					break;
			case 3: di.setTitle("Error").setMessage("Field c1:\n"+err).show();
					break;
			case 4:	di.setTitle("Error").setMessage("Field a2:\n"+err).show();
					break;
			case 5: di.setTitle("Error").setMessage("Field b2:\n"+err).show();
					break;
			case 6: di.setTitle("Error").setMessage("Field c2:\n"+err).show();
					break;
			}
		}else{
			double den=b1*a2-b2*a1;
			if(den!=0){
				double x=(b1*c2-b2*c1)/(den);
				tv1.setText(""+x);
				double y=(c1-a1*x)/b1;
				tv2.setText(""+y);
			}else{
				tv1.setText("They are equations of parallel line");
				tv2.setText("");
			}
		}
	}

}
