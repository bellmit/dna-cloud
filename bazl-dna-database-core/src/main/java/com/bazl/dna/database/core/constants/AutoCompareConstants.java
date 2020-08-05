package com.bazl.dna.database.core.constants;

import com.bazl.dna.common.PublicConstants;

public class AutoCompareConstants extends PublicConstants {
	
	/**
	 * 查询数不能大于线程数
	 */
	public static final int COMPARE_PAGE_SIZE = 10;
	
	/**
	 * 队列类型
	 */
	public static final String QUEUE_TYPE = "autoCompareDirect";
	
	/**
	 * 队列名称
	 */
	public static final String QUEUE_NAME = "autoCompareQueue";
	
	/**
	 * 队列标识
	 */
	public static final String QUEUE_KEY = "autoCompareKey";
	
	/**
	 * 快速比对 str
	 */
	public static final String QUEUE_TYPE_AUTO_STR = QUEUE_TYPE + "QueueStr";
	public static final String QUEUE_NAME_AUTO_STR = QUEUE_NAME + "Str";
	public static final String QUEUE_KEY_AUTO_STR_SAVE_MATCH_RESULT = QUEUE_NAME + "StrSaveMatchResult";
	public static final String QUEUE_KEY_AUTO_STR_COMPARE = QUEUE_NAME + "StrCompare";
	
	/**
	 * 快速比对 ystr
	 */
	public static final String QUEUE_TYPE_AUTO_YSTR = QUEUE_TYPE + "QueueYstr";
	public static final String QUEUE_NAME_AUTO_YSTR = QUEUE_NAME + "Ystr";
	public static final String QUEUE_KEY_AUTO_YSTR_SAVE_MATCH_RESULT = QUEUE_NAME + "YstrSaveMatchResult";
	public static final String QUEUE_KEY_AUTO_YSTR_COMPARE = QUEUE_NAME + "YstrCompare";
	
	/**
	 * 快速比对 mix
	 */
	public static final String QUEUE_TYPE_AUTO_MIX = QUEUE_TYPE + "QueueMix";
	public static final String QUEUE_NAME_AUTO_MIX = QUEUE_NAME + "Mix";
	public static final String QUEUE_KEY_AUTO_MIX_SAVE_MATCH_RESULT = QUEUE_NAME + "MixSaveMatchResult";
	public static final String QUEUE_KEY_AUTO_MIX_COMPARE = QUEUE_NAME + "MixCompare";
	
	/**
	 * 快速比对 relative three conjoined
	 */
	public static final String QUEUE_TYPE_AUTO_RELATIVE_THREE_CONJOINED = QUEUE_TYPE + "QueueThreeConjoined";
	public static final String QUEUE_NAME_AUTO_RELATIVE_THREE_CONJOINED = QUEUE_NAME + "RelativeThreeConjoined";
	public static final String QUEUE_KEY_AUTO_RELATIVE_THREE_CONJOINED_SAVE_MATCH_RESULT = QUEUE_NAME + "ThreeConjoinedSaveMatchResult";
	public static final String QUEUE_KEY_AUTO_RELATIVE_THREE_CONJOINED_COMPARE = QUEUE_NAME + "ThreeConjoinedCompare";

	/**
	 * 快速比对 relative single conjoined
	 */
	public static final String QUEUE_TYPE_AUTO_RELATIVE_SINGLE_CONJOINED = QUEUE_TYPE + "QueueSingleConjoined";
	public static final String QUEUE_NAME_AUTO_RELATIVE_SINGLE_CONJOINED = QUEUE_NAME + "RelativeSingleConjoined";
	public static final String QUEUE_KEY_AUTO_RELATIVE_SINGLE_CONJOINED_SAVE_MATCH_RESULT = QUEUE_NAME + "SingleConjoinedSaveMatchResult";
	public static final String QUEUE_KEY_AUTO_RELATIVE_SINGLE_CONJOINED_COMPARE = QUEUE_NAME + "SingleConjoinedCompare";

	/**
	 * Constructor
	 */
	private AutoCompareConstants() {
		super();
	}
}
