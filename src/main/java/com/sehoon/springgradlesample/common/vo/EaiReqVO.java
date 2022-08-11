package com.sehoon.springgradlesample.common.vo;

import com.sehoon.springgradlesample.common.mciv2.vo.MciCommHeaderVO;
import com.sehoon.springgradlesample.common.mciv2.vo.MciHfldMsgVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class EaiReqVO<T>{
    private MciCommHeaderVO tgrmCmnnhddvValu;

    private MciHfldMsgVO tgrmMsdvValu;

    private T tgrmDtdvValu;

    public EaiReqVO(boolean createDefaultObject){
        if(createDefaultObject){
            createDefaultObject();
        }
    }

    private void createDefaultObject(){
        this.tgrmCmnnhddvValu = new MciCommHeaderVO();
        this.tgrmMsdvValu = new MciHfldMsgVO();
    }
}
