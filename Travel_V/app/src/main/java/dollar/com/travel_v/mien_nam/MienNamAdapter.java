package dollar.com.travel_v.mien_nam;

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
 * Created by anhch_000 on 30/03/2017.
 */

public class MienNamAdapter extends RecyclerView.Adapter<MienNamAdapter.ViewHodel> {

    private Context context;
    private ArrayList<MienNam> mienNams;

    public MienNamAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHodel onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHodel(LayoutInflater.from(parent.getContext()).inflate(R.layout.travel_item_mien_nam, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHodel holder, int position) {
        MienNam mienNam = mienNams.get(position);

        holder.id.setText(String.valueOf(mienNam.getId()));
        holder.name.setText(mienNam.getName());
        holder.title.setText(mienNam.getTitle());
        Glide.with(context)
                .load(mienNam.getImageString())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (mienNams != null){
            return mienNams.size();
        }else {
            return 0;
        }
    }

    public void setMienNams(ArrayList<MienNam> mienNams) {
        this.mienNams = mienNams;
    }

    public class ViewHodel extends RecyclerView.ViewHolder{
        private TextView id;
        private ImageView imageView;
        private TextView name;
        private TextView title;
        public ViewHodel(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.txt_id);
            name = (TextView) itemView.findViewById(R.id.txt_name);
            title = (TextView) itemView.findViewById(R.id.txt_title);
            imageView = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }
}
