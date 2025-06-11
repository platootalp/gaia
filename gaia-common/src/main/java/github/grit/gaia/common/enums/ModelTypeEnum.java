package github.grit.gaia.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum ModelTypeEnum {

	LLM("llm", "对话模型", "通用语言模型，支持聊天与生成"),
	EMBEDDING("embedding", "向量生成", "用于文本向量化，如知识库索引"),
	RERANK("rerank", "重排序", "对检索结果进行打分与排序"),
	TEXT_TO_IMAGE("text-to-image", "文生图", "根据文本生成图片"),
	ASR("asr", "语音识别", "将语音内容转为文字"),
	TTS("tts", "语音合成", "将文本内容转为语音"),
	OCR("ocr", "文档识别", "识别图片或PDF中的文字");

	private final String code;
	private final String name;
	private final String desc;
}
