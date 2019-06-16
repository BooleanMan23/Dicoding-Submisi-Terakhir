package com.example.dicoding_submisi2;

        import android.content.Context;
        import android.content.Intent;
        import android.support.annotation.NonNull;
        import android.support.v7.widget.CardView;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.util.ArrayList;
        import com.bumptech.glide.Glide;
        import com.bumptech.glide.request.RequestOptions;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    ArrayList<Item> Items;
    private Context context;

    public MyAdapter(ArrayList<Item> Items, Context context) {
        this.Items = Items;
        this.context = context;
    }

    public ArrayList<Item> getListItem(){
        return Items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from( viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);

        return  new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.i("onBindViewHolder", "true");
        final Item Item = getListItem().get(i);


        viewHolder.posterImageView.setImageResource(Item.getPoster());
        viewHolder.judulTextView.setText(Item.getJudul());
        viewHolder.deskripsiTextView.setText(Item.getDeskripsi());
        viewHolder.tanggalRilisTextView.setText(Item.getTanggalRilis());

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailItemActivity.class);
                i.putExtra("Item",Item );
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        public ImageView posterImageView;
        public TextView judulTextView;
        public  TextView deskripsiTextView;
        public TextView tanggalRilisTextView;
        public CardView cardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            posterImageView = (ImageView) itemView.findViewById(R.id.posterImageView);
            Log.i("view holder", "true");
            judulTextView = (TextView) itemView.findViewById(R.id.judulTextView);
            deskripsiTextView = (TextView) itemView.findViewById(R.id.deskripsiTextView);
            tanggalRilisTextView = (TextView) itemView.findViewById(R.id.tanggalRilisTextView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);



        }
    }


}
