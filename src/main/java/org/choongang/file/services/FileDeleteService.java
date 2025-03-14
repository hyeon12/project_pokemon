package org.choongang.file.services;

import lombok.RequiredArgsConstructor;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.exceptions.FileNotFoundException;
import org.choongang.file.mappers.FileInfoMapper;
import org.choongang.global.config.annotations.Service;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileDeleteService {
    private final FileInfoMapper mapper;
    private final FileInfoService infoService;

    /**
     * 파일 1개 삭제
     * @param seq  : 파일 등록번호
     */
    public void delete(long seq){
        FileInfo data = infoService.get(seq).orElseThrow(FileNotFoundException::new);
        delete(data);
    }

    public void delete(FileInfo data){
        String filePath = data.getFilePath(); // 서버에 올라가 있는 파일 경로
        File file = new File(filePath);
        if(file.exists()){
            file.delete();
        }

        mapper.delete(data.getSeq());
    }

    /**
     * 파일 목록 삭제
     *
     * @param gid
     * @param location
     */
    public void deletes(String gid, String location){
        List<FileInfo> items = infoService.getList(gid, location);
        items.forEach(this::delete);
    }

    public void deletes(String gid){
        deletes(gid, null);
    }

}
