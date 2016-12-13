package com.epam.rd.frontend.service;

import com.epam.rd.frontend.component.ProgramInfo;

public interface ProgramInfoService {

    ProgramInfo getProgramInfoByProgramId(Long programId);

    ProgramInfo getProgramInfoByModuleId(Long moduleId);
}
