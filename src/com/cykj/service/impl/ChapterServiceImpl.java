package com.cykj.service.impl;

import com.cykj.dao.IChapterDao;
import com.cykj.dao.Impl.ChapterDao;
import com.cykj.net.ResponseDto;
import com.cykj.pojo.Chapter;
import com.cykj.service.ChapterService;

import java.util.List;

/**
 * Description:
 * 实例化的章节Service层
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/23 16:56
 */
public class ChapterServiceImpl implements ChapterService {
    @Override
    public ResponseDto getCourseChapters(int id) {
        IChapterDao chapterDao = ChapterDao.getInstance();
        ResponseDto responseDto;
        List<Chapter> chapters = chapterDao.getCourseChapters(id);
        if (chapters != null) {
            responseDto = new ResponseDto(1, "get chapters successfully!", chapters);
        } else {
            responseDto = new ResponseDto(0, "fail to get chapters!", null);
        }
        return responseDto;
    }
}
