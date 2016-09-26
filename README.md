# ATPointView
Android动画之自定义Evaluator实现弹球效果

## Screenshots
![](https://github.com/kuangxiaoguo0123/ATPointView/raw/master/gifFile/pointView.gif)

## attrs定义
````
<!--point_color是弹球颜色-->

<declare-styleable name="PointView">
     <attr name="point_color" format="color" />
</declare-styleable>
````
## xml引用
````
<com.asiatravel.atpointview.view.PointView
        android:id="@+id/third_point_view"
        android:layout_width="150dp"
        android:layout_height="200dp"
        android:layout_below="@id/second_point_view"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="10dp"
        android:background="@color/colorPrimary"
        app:point_color="#0ff" />
````
## PointView中的startAnimation()方法
````
public void startAnimation() {
        /**
         * 使用ValueAnimator.ofObject()方法并使用自定义的Evaluator实现动画
         */
        ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(), new Point(0), new Point(minValue * 2 / 5));
        animator.setDuration(2000);
        /**
         * 设置插值器为BounceInterpolator,其效果为:动画结束的时候弹起
         */
        animator.setInterpolator(new BounceInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                /**
                 * 通过getAnimatedValue获取我们变化中的Point对象
                 */
                mPoint = (Point) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }
````
## 实现弹球动画
**获取PointView的实例，然后直接调用上面的startAnimation()方法**

**MainActivity.java**
````
    private PointView pointView;
    private PointView secondPointView;
    private PointView thirdPointView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pointView = (PointView) findViewById(R.id.point_view);
        secondPointView = (PointView) findViewById(R.id.second_point_view);
        thirdPointView = (PointView) findViewById(R.id.third_point_view);

        findViewById(R.id.animation_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pointView.startAnimation();
                secondPointView.startAnimation();
                thirdPointView.startAnimation();
            }
        });
    }
````
