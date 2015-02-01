package com.shantanu.eqnsolver;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import de.congrace.exp4j.ExpressionBuilder;

public class Quad extends Activity implements OnClickListener, OnSeekBarChangeListener {
	
	TextView tv1,tv2,tv3, t_seek;
	SeekBar seek;
	EditText et1, et2, et3;
	ImageView im;
	Handler handle;
	int acc=4;
	
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		this.setContentView(R.layout.quad);
		TextView tv=(TextView) this.findViewById(R.id.s3_textView);
		im=(ImageView) this.findViewById(R.id.s3_imageView1);
		tv1=(TextView) this.findViewById(R.id.s3_textView1);
		tv2=(TextView) this.findViewById(R.id.s3_textView2);
		tv3=(TextView) this.findViewById(R.id.s3_textView3);
		t_seek=(TextView) this.findViewById(R.id.s3_textViewprogress);
		et1=(EditText) this.findViewById(R.id.s3_editText1);
		et2=(EditText) this.findViewById(R.id.s3_editText2);
		et3=(EditText) this.findViewById(R.id.s3_editText3);
		seek=(SeekBar) this.findViewById(R.id.s3_seekBar1);
		seek.setOnSeekBarChangeListener(this);
		tv.setText(Html.fromHtml("A quadratic equation is of the form ax<sup>2</sup>+bx+c=0"));
		Button but=(Button) this.findViewById(R.id.s3_button1);
		but.setOnClickListener(this);
		handle=new Handler(){
			public void handleMessage(Message msg){
				if(msg.what==1){
					im.setImageDrawable((Drawable) msg.obj);
					Toast.makeText(Quad.this, "Graph loaded", Toast.LENGTH_SHORT).show();
				}else
					Toast.makeText(Quad.this, "Unable to load graph", Toast.LENGTH_SHORT).show();
			}	
		};
		if(icicle!=null){
			tv1.setText(icicle.getString("1"));
			tv2.setText(icicle.getString("2"));
			tv3.setText(icicle.getString("v"));
		}
		et1.setSingleLine(true);
		et2.setSingleLine(true);
		et3.setSingleLine(true);
	}
	
	public void onSaveInstanceState(Bundle save){
		super.onSaveInstanceState(save);
		save.putString("1", tv1.getText().toString());
		save.putString("2", tv2.getText().toString());
		save.putString("v", tv3.getText().toString());
	}
	
	public void onDestroy(){
		super.onDestroy();
	}
	
	private double trunc(int d, double val){
		double d1= val* (Math.pow(10, d));
		double d2= Math.rint(d1)/Math.pow(10, d);
		return d2;
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		double a=0, b=0, c=0;
		Exception ex=null;
		int i=1;
		try {
			a=new ExpressionBuilder(et1.getText().toString()).withVariable("pi", Math.PI).withVariable("e", Math.E).build().calculate();i++;
			b=new ExpressionBuilder(et2.getText().toString()).withVariable("pi", Math.PI).withVariable("e", Math.E).build().calculate();i++;
			c=new ExpressionBuilder(et3.getText().toString()).withVariable("pi", Math.PI).withVariable("e", Math.E).build().calculate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ex=e;
		}
		if(ex!=null){
			AlertDialog.Builder di=new AlertDialog.Builder(Quad.this);
			String err=ex.toString().replaceFirst("de.congrace.exp4j.", "");
			switch(i){
			case 1: di.setTitle("Error").setMessage("Field a:\n"+err).show();
					break;
			case 2: di.setTitle("Error").setMessage("Field b:\n"+err).show();
					break;
			case 3: di.setTitle("Error").setMessage("Field c:\n"+err).show();
					break;
			}
		}else{
			if(a==0)
				Toast.makeText(this, "Not a quadratic equation", Toast.LENGTH_LONG).show();
			else{
				double dis=b*b-4*a*c;
				double root;
				if(dis>=0){
					root=(-b+Math.sqrt(dis))/(2*a);
					tv1.setText(""+trunc(acc, root));
					root=(-b-Math.sqrt(dis))/(2*a);
					tv2.setText(""+trunc(acc, root));
				}else{
					dis=-1*dis;
					root=-b/(2*a);
					tv1.setText(""+trunc(acc, root)+"+i("+trunc(acc, Math.sqrt(dis)/(2*a))+")");
					tv2.setText(""+trunc(acc, root)+"-i("+trunc(acc, Math.sqrt(dis)/(2*a))+")");
				}
				tv3.setText("("+((-1*b)/(2*a))+", "+((-1*dis)/(4*a))+")");
			}
		}
	}

	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub
		acc=arg1;
		t_seek.setText("Accuracy: "+acc+" decimal places");
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

}
