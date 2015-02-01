package com.shantanu.eqnsolver;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class EqnSolverActivity extends Activity implements OnClickListener, android.content.DialogInterface.OnClickListener {
    /** Called when the activity is first created. */
	
	Button b1, b2, b3, b4;
	Builder dial;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        b1=(Button) this.findViewById(R.id.s1_button1);
        b1.setOnClickListener(this);
        b2=(Button) this.findViewById(R.id.s1_button2);
        b2.setOnClickListener(this);
        b3=(Button) this.findViewById(R.id.s1_button3);
        b3.setOnClickListener(this);
        b4=(Button) this.findViewById(R.id.s1_button4);
        b4.setOnClickListener(this);
        dial = new Builder(this);
		dial.setTitle("Confirm").setMessage("Are you sure you want to quit?");
		dial.setPositiveButton("YES", this);
		dial.setNegativeButton("NO", null);
    }
    
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0==b1){
			Intent i=new Intent(EqnSolverActivity.this, Linear.class);
			startActivity(i);
		}else if(arg0==b2){
			Intent i=new Intent(EqnSolverActivity.this, Quad.class);
			startActivity(i);
		}else if(arg0==b3){
			Intent i=new Intent(EqnSolverActivity.this, Cub.class);
			startActivity(i);
		}else if(arg0==b4){
			Intent i=new Intent(EqnSolverActivity.this, Expr.class);
			startActivity(i);
		}
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
		dial.show();
		return false;
	}
	
	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		this.finish();
	}
}