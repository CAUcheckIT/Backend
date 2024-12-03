package capstone.checkIT.apipayLoad.handler;

import capstone.checkIT.apipayLoad.code.BaseCode;
import capstone.checkIT.apipayLoad.code.status.ErrorStatus;
import capstone.checkIT.exception.GeneralException;


public class TempHandler extends GeneralException {
    public TempHandler(BaseCode errorCode) { super((ErrorStatus) errorCode); }
}
