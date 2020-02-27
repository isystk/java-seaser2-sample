package com.isystk.sample.batch.job;

import com.isystk.sample.batch.BaseBatch;
import com.isystk.sample.batch.Status;
import com.isystk.sample.batch.logic.Bat100Logic;

/**
 * BAT100_インデックス_全更新
 *
 * @author iseyoshitaka
 *
 */
public class Bat100Job extends BaseBatch {

    /**
     * コンストラクタ。
     *
     */
    public Bat100Job() {
	super();
    }

    @Override
    protected String getBatchName() {
	return "BAT100_インデックス_全更新";
    }

    @Override
    protected void execute() {
	Bat100Logic bat100Logic = getComponent(Bat100Logic.class);
	bat100Logic.execute();
    }

    public static void main(String[] args) {
	Status status = new Bat100Job().run();
	System.exit(status.getCode());
    }
}
