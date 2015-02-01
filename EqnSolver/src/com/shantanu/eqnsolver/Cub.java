package com.shantanu.eqnsolver;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import de.congrace.exp4j.ExpressionBuilder;
import edu.rit.numeric.Cubic;

public class Cub extends Activity implements OnClickListener, OnSeekBarChangeListener{
	
	EditText et1, et2, et3, et4;
	TextView t2, t3, t4, tseek;
	SeekBar seek;
	Button b1;
	int acc=4;
	
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		setContentView(R.layout.cube);
		et1=(EditText) findViewById(R.id.s4_editText1);
		et2=(EditText) findViewById(R.id.s4_editText2);
		et3=(EditText) findViewById(R.id.s4_editText3);
		et4=(EditText) findViewById(R.id.s4_editText4);
		TextView t1=(TextView) findViewById(R.id.s4_textView1);
		t2=(TextView) findViewById(R.id.s4_textView2);
		t3=(TextView) findViewById(R.id.s4_textView3);
		t4=(TextView) findViewById(R.id.s4_textView4);
		tseek=(TextView) findViewById(R.id.s4_textViewseek);
		seek=(SeekBar) findViewById(R.id.s4_seekBar1);
		b1=(Button) findViewById(R.id.s4_button1);
		b1.setOnClickListener(this);
		seek.setOnSeekBarChangeListener(this);
		t1.setText(Html.fromHtml("A cubic equation is of the form ax<sup>3</sup>+bx<sup>2</sup>+cx+d=0"));
		et1.setSingleLine(true);
		et2.setSingleLine(true);
		et3.setSingleLine(true);
		et4.setSingleLine(true);
		if(icicle!=null){
			t2.setText(icicle.getString("1"));
			t3.setText(icicle.getString("2"));
			t4.setText(icicle.getString("3"));
		}
	}
	
	public void onDestroy(){
		super.onDestroy();
	}
	
	public void onSaveInstanceState(Bundle save){
		super.onSaveInstanceState(save);
		save.putString("1", t2.getText().toString());
		save.putString("2", t3.getText().toString());
		save.putString("3", t4.getText().toString());
	}
	
	private double trunc(int d, double val){
		double d1= val* (Math.pow(10, d));
		double d2= Math.rint(d1)/Math.pow(10, d);
		return d2;
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		double a=0, b=0, c=0, d=0;
		Exception ex=null;
		int i=1;
		try {
			a=new ExpressionBuilder(et1.getText().toString()).withVariable("pi", Math.PI).withVariable("e", Math.E).build().calculate();i++;
			b=new ExpressionBuilder(et2.getText().toString()).withVariable("pi", Math.PI).withVariable("e", Math.E).build().calculate();i++;
			c=new ExpressionBuilder(et3.getText().toString()).withVariable("pi", Math.PI).withVariable("e", Math.E).build().calculate();i++;
			d=new ExpressionBuilder(et4.getText().toString()).withVariable("pi", Math.PI).withVariable("e", Math.E).build().calculate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ex=e;
		}
		if(ex!=null){
			AlertDialog.Builder di=new AlertDialog.Builder(Cub.this);
			String err=ex.toString().replaceFirst("de.congrace.exp4j.", "");
			switch(i){
			case 1: di.setTitle("Error").setMessage("Field a:\n"+err).show();
					break;
			case 2: di.setTitle("Error").setMessage("Field b:\n"+err).show();
					break;
			case 3: di.setTitle("Error").setMessage("Field c:\n"+err).show();
					break;
			case 4: di.setTitle("Error").setMessage("Field d:\n"+err).show();
					break;
			}
		}else{
			if(a==0)
				Toast.makeText(this, "Not a cubic equation", Toast.LENGTH_LONG).show();
			else{
				Cubic cubic=new Cubic();
				cubic.solve(a, b, c, d);
				t2.setText(""+trunc(acc, cubic.x1));
				String[] res=quad(a, (b+a*cubic.x1), (cubic.x1*cubic.x1+cubic.x1*b+c));
				t3.setText(res[0]);
				t4.setText(res[1]);
			}
		}
	}
	
	String[] quad(double a, double b, double c){
		double dis=b*b-4*a*c;
		double root;
		String[] res=new String[2];
		if(dis>=0){
			root=(-b+Math.sqrt(dis))/(2*a);
			res[0]=""+trunc(acc, root);
			root=(-b-Math.sqrt(dis))/(2*a);
			res[1]=""+trunc(acc, root);
		}else{
			dis=-1*dis;
			root=-b/(2*a);
			res[0]=""+trunc(acc, root)+"+i("+trunc(acc, Math.sqrt(dis)/(2*a))+")";
			res[1]=""+trunc(acc, root)+"-i("+trunc(acc, Math.sqrt(dis)/(2*a))+")";
		}
		return res;
	}

	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub
		tseek.setText("Accuracy: "+arg1+" decimal places");
		acc=arg1;
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
