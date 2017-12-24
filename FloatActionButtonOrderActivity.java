public class FloatActionButtonOrderActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityFloatmenuorderBinding activityFloatmenuBinding;
    private static final int ITEM_DISTANCE = 200;//每个Item之间的间距
    private boolean isOpen;//是否展开
    private boolean isAnimating;//是否正在执行动画，防止动画执行错乱

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFloatmenuBinding = DataBindingUtil.setContentView(this, R.layout.activity_floatmenuorder);
        activityFloatmenuBinding.startIv.setOnClickListener(this);
        activityFloatmenuBinding.startIv1.setOnClickListener(this);
        activityFloatmenuBinding.startIv2.setOnClickListener(this);
        activityFloatmenuBinding.startIv3.setOnClickListener(this);
        activityFloatmenuBinding.startIv4.setOnClickListener(this);
        activityFloatmenuBinding.startIv5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_iv:
                if (isOpen) {
                    doAnimClose();
                } else {
                    doAnimOpen();
                }
                break;
            case R.id.start_iv1:
                doAnimClose();
                MToast(1);
                break;
            case R.id.start_iv2:
                doAnimClose();
                MToast(2);
                break;
            case R.id.start_iv3:
                doAnimClose();
                MToast(3);
                break;
            case R.id.start_iv4:
                doAnimClose();
                MToast(4);
                break;
            case R.id.start_iv5:
                doAnimClose();
                MToast(5);
                break;
            default:
        }
    }

    //view的弹出动画，首先是将view移动至其该所在的位置，其次是大小以及 透明度的变化了
    private AnimatorSet doAnimOpenItem(View view, int index) {
        float distance = index * ITEM_DISTANCE;
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(
                ObjectAnimator.ofFloat(view, "alpha", 0, 1))
                .with(ObjectAnimator.ofFloat(view, "scaleX", 0f, 1))
                .with(ObjectAnimator.ofFloat(view, "scaleY", 0f, 1))
                .after(ObjectAnimator.ofFloat(view, "translationY", 0, distance));
        //设置的延时时间恰好是在前一个动画执行结束前 后一个动画就开始执行，给人一种连贯的感觉
        animatorSet.setStartDelay(index * 40);
        animatorSet.setDuration(50);
        return animatorSet;
    }

    //点击之后执行展开动画的方法
    private void doAnimOpen() {
        //如果正在执行动画则返回
        if (isAnimating) {
            return;
        }
        isAnimating = true;
        isOpen = true;
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                doAnimOpenItem(activityFloatmenuBinding.startIv2, 1),
                doAnimOpenItem(activityFloatmenuBinding.startIv3, 2),
                doAnimOpenItem(activityFloatmenuBinding.startIv4, 3),
                doAnimOpenItem(activityFloatmenuBinding.startIv5, 4));
        animatorSet.setInterpolator(new OvershootInterpolator());
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //动画执行完毕将其置为false
                isAnimating = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    //每个Item关闭时候执行的动画
    private AnimatorSet doAnimCloseItem(View view, int index) {
        float distance = index * ITEM_DISTANCE;
        AnimatorSet animatorSet = new AnimatorSet();
        //首先是将view大小变为0，以及透明度变为0，然后将其移动至原来的位置
        animatorSet.play(
                ObjectAnimator.ofFloat(view, "alpha", 1, 0))
                .with(ObjectAnimator.ofFloat(view, "scaleX", 1, 0f))
                .with(ObjectAnimator.ofFloat(view, "scaleY", 1, 0f))
                .before(ObjectAnimator.ofFloat(view, "translationY", distance, 0));
        animatorSet.setDuration(50);
        //关闭时候恰好跟开始时候是相反的，所以延迟时间是越靠后越少了
        animatorSet.setStartDelay((5 - index) * 40);
        return animatorSet;
    }

    //所有Item执行关闭动画的方法
    private void doAnimClose() {
        //如果正在执行动画则返回
        if (isAnimating) {
            return;
        }
        isAnimating = true;
        isOpen = false;
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                doAnimCloseItem(activityFloatmenuBinding.startIv5, 4),
                doAnimCloseItem(activityFloatmenuBinding.startIv4, 3),
                doAnimCloseItem(activityFloatmenuBinding.startIv3, 2),
                doAnimCloseItem(activityFloatmenuBinding.startIv2, 1));
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //动画执行完毕将其置为false
                isAnimating = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    //点击之后弹出每个Item的下标
    private void MToast(int index) {
        Toast.makeText(this, "click:" + index, Toast.LENGTH_SHORT).show();
    }
}
