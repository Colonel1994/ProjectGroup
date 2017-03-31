package dollar.com.travel_v.mien_bac;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import dollar.com.travel_v.R;

/**
 * Created by anhch_000 on 21/03/2017.
 */

public class
MienBacAdapter extends RecyclerView.Adapter<MienBacAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MienBac> mienBacs;

    public MienBacAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.travel_item_mien_bac, parent, false));
    }

    @Override
    public void onBindViewHolder(MienBacAdapter.ViewHolder holder, int position) {
        MienBac mienBac = mienBacs.get(position);

        holder.id.setText(String.valueOf(mienBac.getId()));
        holder.name.setText(mienBac.getName());
        holder.title.setText(mienBac.getTitle());
        Glide.with(context)
                .load(mienBac.getImageString())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (mienBacs != null){
            return mienBacs.size();
        }else {
            return 0;
        }
    }

    public void setMienBacs(ArrayList<MienBac> mienBacs) {
        this.mienBacs = mienBacs;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView id;
        private ImageView imageView;
        private TextView name;
        private TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.txt_id);
            name = (TextView) itemView.findViewById(R.id.txt_name);
            title = (TextView) itemView.findViewById(R.id.txt_title);
            imageView = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }
}
