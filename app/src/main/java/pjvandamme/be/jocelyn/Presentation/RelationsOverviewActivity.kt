package pjvandamme.be.jocelyn.Presentation

import android.arch.lifecycle.Observer
import android.graphics.Rect
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import pjvandamme.be.jocelyn.R
import android.util.TypedValue
import android.support.v7.widget.RecyclerView
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.ImageView
import pjvandamme.be.jocelyn.Domain.Models.Relation
import pjvandamme.be.jocelyn.Domain.ViewModels.RelationsOverviewViewModel
import pjvandamme.be.jocelyn.R.id.toolbar




class RelationsOverviewActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var adapter: RelationsOverviewRecyclerAdapter? = null
    private var relationList: List<Relation>? = null
    private var relationsOverviewViewModel: RelationsOverviewViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_relations_overview)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // enabling a back button; navigationOnClickListener must be set explicitly for this collapsing toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        initCollapsingToolbar()

        recyclerView = findViewById(R.id.recycler_view)

        relationList = mutableListOf()
        adapter = RelationsOverviewRecyclerAdapter(this)

        val mLayoutManager = GridLayoutManager(this, 2)
        recyclerView!!.layoutManager = mLayoutManager
        recyclerView!!.addItemDecoration(GridSpacingItemDecoration(2, dpToPx(10), true))
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.adapter = adapter

        // initialize viewmodel
        relationsOverviewViewModel = RelationsOverviewViewModel(this.application)

        // set observer to LiveData change
        relationsOverviewViewModel?.allRelations?.observe(this, object: Observer<List<Relation>> {
            override fun onChanged(t: List<Relation>?){
                relationList = t
                // add all relations to the relationsList
                adapter!!.updateRelationSuggestions(t)
            }
        })

        val backdrop: ImageView = findViewById(R.id.backdrop)
        backdrop.setImageResource(R.drawable.elephant_cover)

    }

    // init collapsable toolbar, shows and hides toolbar title when scrolling
    private fun initCollapsingToolbar() {
        val collapsingToolbar = findViewById<View>(R.id.collapsing_toolbar) as CollapsingToolbarLayout
        collapsingToolbar.title = " "
        val appBarLayout = findViewById<View>(R.id.appbar) as AppBarLayout
        appBarLayout.setExpanded(true)

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            internal var isShow = false
            internal var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.title = getString(R.string.toolbar_relations_overview)
                    isShow = true
                } else if (isShow) {
                    collapsingToolbar.title = " "
                    isShow = false
                }
            }
        })
    }

    // decorate recyclerview item: equal margins around grid item
    inner class GridSpacingItemDecoration(
        private val spanCount: Int,
        private val spacing: Int,
        private val includeEdge: Boolean
    ) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val position = parent.getChildAdapterPosition(view) // item position
            val column = position % spanCount // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing
                }
                outRect.bottom = spacing // item bottom
            } else {
                outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing /
                        spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing // item top
                }
            }
        }
    }

    // conversion of dp to regular pixels
    private fun dpToPx(dp: Int): Int {
        val r = resources
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics))
    }
}
