package ru.dvteam.itcollabhub.callbackclasses;

import ru.dvteam.itcollabhub.classmodels.ParticipantWithRole;

public interface CallBackParticipantWithRole {
    void invoke(ParticipantWithRole participant, int r);
}
