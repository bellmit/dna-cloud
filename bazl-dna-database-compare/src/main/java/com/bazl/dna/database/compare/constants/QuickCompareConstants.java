package com.bazl.dna.database.compare.constants;

import com.bazl.dna.common.PublicConstants;

public class QuickCompareConstants extends PublicConstants {
	
	/**
	 * 查询数不能大于线程数
	 */
	public static final int COMPARE_PAGE_SIZE = 10;
	
	/**
	 * 队列类型
	 */
	public static final String QUEUE_TYPE = "quickCompareDirect";
	
	/**
	 * 队列名称
	 */
	public static final String QUEUE_NAME = "quickCompareQueue";
	
	/**
	 * 队列标识
	 */
	public static final String QUEUE_KEY = "quickCompareKey";
	
	/**
	 * 快速比对 str
	 */
	public static final String QUEUE_TYPE_QUICK_STR = QUEUE_TYPE + "QueueStr";
	public static final String QUEUE_NAME_QUICK_STR = QUEUE_NAME + "Str";
	public static final String QUEUE_KEY_QUICK_STR_COMPARE = QUEUE_NAME + "StrCompare";
	
	/**
	 * 快速比对 ystr
	 */
	public static final String QUEUE_TYPE_QUICK_YSTR = QUEUE_TYPE + "QueueYstr";
	public static final String QUEUE_NAME_QUICK_YSTR = QUEUE_NAME + "Ystr";
	public static final String QUEUE_KEY_QUICK_YSTR_COMPARE = QUEUE_NAME + "YstrCompare";
	
	/**
	 * 快速比对 mix
	 */
	public static final String QUEUE_TYPE_QUICK_MIX = QUEUE_TYPE + "QueueMix";
	public static final String QUEUE_NAME_QUICK_MIX = QUEUE_NAME + "Mix";
	public static final String QUEUE_KEY_QUICK_MIX_COMPARE = QUEUE_NAME + "MixCompare";
	
	/**
	 * 快速比对 relative three conjoined
	 */
	public static final String QUEUE_TYPE_QUICK_RELATIVE_THREE_CONJOINED = QUEUE_TYPE + "QueueThreeConjoined";
	public static final String QUEUE_NAME_QUICK_RELATIVE_THREE_CONJOINED = QUEUE_NAME + "RelativeThreeConjoined";
	public static final String QUEUE_KEY_QUICK_RELATIVE_THREE_CONJOINED_COMPARE = QUEUE_NAME + "ThreeConjoinedCompare";

	/**
	 * 快速比对 relative single conjoined
	 */
	public static final String QUEUE_TYPE_QUICK_RELATIVE_SINGLE_CONJOINED = QUEUE_TYPE + "QueueSingleConjoined";
	public static final String QUEUE_NAME_QUICK_RELATIVE_SINGLE_CONJOINED = QUEUE_NAME + "RelativeSingleConjoined";
	public static final String QUEUE_KEY_QUICK_RELATIVE_SINGLE_CONJOINED_COMPARE = QUEUE_NAME + "SingleConjoinedCompare";

	/**
	 * Constructor
	 */
	private QuickCompareConstants() {
		super();
	}
}
