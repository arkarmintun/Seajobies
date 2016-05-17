package com.arkarmintun.seajobies.adapter;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.arkarmintun.seajobies.R;
import com.arkarmintun.seajobies.activity.CertificateEditActivity;
import com.arkarmintun.seajobies.model.Certificate;

import java.util.List;

/**
 * Created by arkar on 5/16/16.
 */
public class CertificateAdapter extends RecyclerView.Adapter<CertificateAdapter.ViewHolder> {

    private List<Certificate> certificates;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtCertificateName;
        public TextView txtCertificateNo;
        public TextView txtIssueDate;
        public TextView txtExpiryDate;
        public TextView txtObjectId;
        public ImageButton btnEdit;

        public ViewHolder(View v) {
            super(v);
            txtCertificateName = (TextView) v.findViewById(R.id.text_certificate_name);
            txtCertificateNo = (TextView) v.findViewById(R.id.text_certificate_number);
            txtIssueDate = (TextView) v.findViewById(R.id.text_issue_date);
            txtExpiryDate = (TextView) v.findViewById(R.id.text_expiry_date);
            txtObjectId = (TextView) v.findViewById(R.id.object_id);
            Typeface robotoRegular = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/Roboto-Regular.ttf");
            this.txtCertificateName.setTypeface(robotoRegular);
            this.txtCertificateNo.setTypeface(robotoRegular);
            this.txtIssueDate.setTypeface(robotoRegular);
            this.txtExpiryDate.setTypeface(robotoRegular);

            btnEdit = (ImageButton) v.findViewById(R.id.btn_edit_certificate);
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), CertificateEditActivity.class);
                    intent.putExtra("objectId", txtObjectId.getText().toString());
                    intent.putExtra("txtCertificateName", txtCertificateName.getText().toString());
                    intent.putExtra("txtCertificateNo", txtCertificateNo.getText().toString());
                    intent.putExtra("txtIssueDate", txtIssueDate.getText().toString());
                    intent.putExtra("txtExpiryDate", txtExpiryDate.getText().toString());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    public CertificateAdapter(List<Certificate> certificates) {
        this.certificates = certificates;
    }

    @Override
    public CertificateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_certificate, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.txtObjectId.setText(certificates.get(position).objectId);
        holder.txtCertificateName.setText(certificates.get(position).certificateName);
        holder.txtCertificateNo.setText(certificates.get(position).certificateNo);
        holder.txtIssueDate.setText(certificates.get(position).issueDate.toString());
        holder.txtExpiryDate.setText(certificates.get(position).expiryDate.toString());
    }

    @Override
    public int getItemCount() {
        return certificates.size();
    }
}
