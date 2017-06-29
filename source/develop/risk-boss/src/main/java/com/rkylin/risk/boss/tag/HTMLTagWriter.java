package com.rkylin.risk.boss.tag;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.io.Writer;
import java.util.Stack;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 15-1-27 下午4:20 version: 1.0
 */
public class HTMLTagWriter {
  /**
   * The {@link SafeWriter} to write to.
   */
  private final SafeWriter writer;

  /**
   * Stores {@link TagStateEntry tag state}. Stack model naturally supports tag nesting.
   */
  private final Stack<TagStateEntry> tagState = new Stack<TagStateEntry>();

  /**
   * Create a new instance of the {@link HTMLTagWriter} class that writes to the supplied {@link
   * PageContext}.
   *
   * @param pageContext the JSP PageContext to obtain the {@link Writer} from
   */
  public HTMLTagWriter(PageContext pageContext) {
    Assert.notNull(pageContext, "PageContext must not be null");
    this.writer = new SafeWriter(pageContext);
  }

  /**
   * Create a new instance of the {@link HTMLTagWriter} class that writes to the supplied {@link
   * Writer}.
   *
   * @param writer the {@link Writer} to write tag content to
   */
  public HTMLTagWriter(Writer writer) {
    Assert.notNull(writer, "Writer must not be null");
    this.writer = new SafeWriter(writer);
  }

  /**
   * 根据给出的表签名开始一个标签 先把当前标签压入栈中，如果栈中有标签且未关闭，先关闭之前的标签，然后再开始新标签
   *
   * @throws JspException
   */
  public void startTag(String tagName) throws JspException {
    if (inTag()) {
      closeTagAndMarkAsBlock();
    }
    push(tagName);
    this.writer.append("<").append(tagName);
  }

  /**
   * 给当前标签添加属性
   *
   * @throws IllegalStateException if the opening tag is closed
   */
  public void writeAttribute(String attributeName, String attributeValue) throws JspException {
    if (currentState().isBlockTag()) {
      throw new IllegalStateException("Cannot write attributes after opening tag is closed.");
    }
    this.writer.append(" ").append(attributeName).append("=\"")
        .append(attributeValue).append("\"");
  }

  public void writeAttribute(String attributeName) throws JspException {
    if (currentState().isBlockTag()) {
      throw new IllegalStateException("Cannot write attributes after opening tag is closed.");
    }
    this.writer.append(" ").append(attributeName);
  }

  /**
   * 如果标签的属性值不为空，则添加属性
   *
   * @see #writeAttribute(String, String)
   */
  public void writeOptionalAttributeValue(String attributeName, String attributeValue)
      throws JspException {
    if (StringUtils.isNotBlank(attributeValue)) {
      writeAttribute(attributeName, attributeValue);
    }
  }

  /**
   * 给标签添加值作为html的inner text
   *
   * @throws IllegalStateException if no tag is open
   */
  public void appendValue(String value) throws JspException {
    if (!inTag()) {
      throw new IllegalStateException("Cannot write tag value. No open tag available.");
    }
    closeTagAndMarkAsBlock();
    this.writer.append(value);
  }

  /**
   * 结束当前标签块
   */
  public void forceBlock() throws JspException {
    if (currentState().isBlockTag()) {
      return; // just ignore since we are already in the block
    }
    closeTagAndMarkAsBlock();
  }

  /**
   * 结束标签以/>结尾
   */
  public void endTag() throws JspException {
    endTag(false);
  }

  /**
   * 关闭标签 true 以<a></a>的形式结束
   */
  public void endTag(boolean enforceClosingTag) throws JspException {
    if (!inTag()) {
      throw new IllegalStateException("Cannot write end of tag. No open tag available.");
    }
    boolean renderClosingTag = true;
    if (!currentState().isBlockTag()) {
      // Opening tag still needs to be closed...
      if (enforceClosingTag) {
        this.writer.append(">");
      } else {
        this.writer.append("/>");
        renderClosingTag = false;
      }
    }
    if (renderClosingTag) {
      this.writer.append("</").append(currentState().getTagName()).append(">");
    }
    this.tagState.pop();
  }

  /**
   * Adds the supplied tag name to the {@link #tagState tag state}.
   */
  private void push(String tagName) {
    this.tagState.push(new TagStateEntry(tagName));
  }

  /**
   * Closes the current opening tag and marks it as a block tag.
   */
  private void closeTagAndMarkAsBlock() throws JspException {
    if (!currentState().isBlockTag()) {
      currentState().markAsBlockTag();
      this.writer.append(">");
    }
  }

  private boolean inTag() {
    return !this.tagState.isEmpty();
  }

  private TagStateEntry currentState() {
    return this.tagState.peek();
  }

  /**
   * Holds state about a tag and its rendered behavior.
   */
  private static class TagStateEntry {

    private final String tagName;

    private boolean blockTag;

    private TagStateEntry(String tagName) {
      this.tagName = tagName;
    }

    private String getTagName() {
      return this.tagName;
    }

    private void markAsBlockTag() {
      this.blockTag = true;
    }

    private boolean isBlockTag() {
      return this.blockTag;
    }
  }

  /**
   * Simple {@link Writer} wrapper that wraps all {@link IOException IOExceptions} in {@link
   * JspException JspExceptions}.
   */
  private static final class SafeWriter {

    private PageContext pageContext;

    private Writer writer;

    private SafeWriter(PageContext pageContext) {
      this.pageContext = pageContext;
    }

    private SafeWriter(Writer writer) {
      this.writer = writer;
    }

    private SafeWriter append(String value) throws JspException {
      try {
        getWriterToUse().write(String.valueOf(value));
        return this;
      } catch (IOException ex) {
        throw new JspException("Unable to write to JspWriter", ex);
      }
    }

    private Writer getWriterToUse() {
      return this.pageContext != null ? this.pageContext.getOut() : this.writer;
    }
  }
}
