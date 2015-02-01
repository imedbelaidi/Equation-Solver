package com.shantanu.eqnsolver;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import de.congrace.exp4j.ExpressionBuilder;

public class Expr extends Activity implements OnClickListener {

	Button b1, b2;
	EditText e1, e2;
	int num=0;
	LinearLayout ll;
	String arr[]=null;
	
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		setContentView(R.layout.calc);
		ll=(LinearLayout) findViewById(R.id.s5_ll);
		b1=(Button) findViewById(R.id.s5_button1);
		b2=(Button) findViewById(R.id.s5_button2);
		e1=(EditText) findViewById(R.id.s5_editText1);
		e2=(EditText) findViewById(R.id.s5_editText2);
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		if(icicle!=null){
			arr=icicle.getStringArray("arr");
			num=arr.length;
			LayoutInflater lf=getLayoutInflater();
			for(int i=num; i>=1; i--){
				LinearLayout l=(LinearLayout) lf.inflate(R.layout.var, null, false);
				TextView t=(TextView) l.findViewById(R.id.var_textView1);
				t.setText("With variable 'a"+i+"' as: ");
				ll.addView(l, 4);
			}
		}
	}
	
	public void onResume(){
		super.onResume();
		if(arr!=null)
		for(int i=0; i<num; i++){
			LinearLayout l=(LinearLayout) ll.getChildAt(4+i);
			EditText e=(EditText) l.findViewById(R.id.var_editText1);
			e.setText(arr[i]);
		}
		arr=null;
	}

	public void onSaveInstanceState(Bundle save){
		super.onSaveInstanceState(save);
		String arr[]=new String[num];
		for(int i=0; i<num; i++){
			LinearLayout l=(LinearLayout) ll.getChildAt(4+i);
			EditText e=(EditText) l.findViewById(R.id.var_editText1);
			arr[i]=e.getText().toString();
		}
		save.putStringArray("arr", arr);
	}
	
	public void onDestroy(){
		super.onDestroy();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0==b1){
			for(int i=0; i<num; i++){
				ll.removeViewAt(4);
			}
			try{
				num=Integer.parseInt(e1.getText().toString());
				LayoutInflater lf=getLayoutInflater();
				for(int i=num; i>=1; i--){
					LinearLayout l=(LinearLayout) lf.inflate(R.layout.var, null, false);
					TextView t=(TextView) l.findViewById(R.id.var_textView1);
					t.setText("With variable 'a"+i+"' as: ");
					ll.addView(l, 4);
				}
			}catch(Exception e){
				e.printStackTrace();
				Toast.makeText(this, "Input is invalid", Toast.LENGTH_LONG).show();
			}
		}else{
			String exp=e2.getText().toString();
			ExpressionBuilder ex=new ExpressionBuilder(exp);
			boolean er=false;
			for(int i=0; i<num; i++){
				LinearLayout l=(LinearLayout) ll.getChildAt(4+i);
				try {
					double a=new ExpressionBuilder(
							((EditText)l.findViewById(R.id.var_editText1)).getText().toString()).
							withVariable("pi", Math.PI).withVariable("e", Math.E).build().calculate();
					ex.withVariable("a"+(i+1), a);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					er=true;
					AlertDialog.Builder d=new Builder(this);
					String err=e.toString().replaceFirst("de.congrace.exp4j.", "");
					d.setTitle("Error").setMessage("Field a"+(i+1)+"\n"+err).show();
					break;
				}
			}
			ex.withVariable("pi", Math.PI).withVariable("e", Math.E);
			if(!er)
			try {
				double res=ex.build().calculate();
				AlertDialog.Builder d=new Builder(this);
				d.setTitle("Result").setMessage(""+res).show();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AlertDialog.Builder d=new Builder(this);
				String err=e.toString().replaceFirst("de.congrace.exp4j.", "");
				d.setTitle("Error").setMessage("Error in expression\n"+err).show();
			}
		}
	}
}
