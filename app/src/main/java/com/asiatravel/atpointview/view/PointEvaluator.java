package com.asiatravel.atpointview.view;

import android.animation.TypeEvaluator;

import com.asiatravel.atpointview.model.Point;

/**
 * Created by kuangxiaoguo on 16/9/26.
 */

class PointEvaluator implements TypeEvaluator<Point> {

    /**
     * @param fraction   动画变化中的浮点参数,0-1
     * @param startValue 动画开始时的Point对象
     * @param endValue   动画结束时的Point对象
     * @return 动画过程中通过计算获取半径并返回一个新的Point对象
     */
    @Override
    public Point evaluate(float fraction, Point startValue, Point endValue) {
        int startRadius = startValue.getRadius();
        int endRadius = endValue.getRadius();
        int newRadius = (int) (startRadius + fraction * (endRadius - startRadius));
        return new Point(newRadius);
    }
}
