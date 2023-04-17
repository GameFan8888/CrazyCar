using System.Collections;
using System.Collections.Generic;
using QFramework;
using UnityEngine;

public class AssetsUpdateState : AbstractState<LaunchStates, LaunchFSM>, IController {
    public AssetsUpdateState(FSM<LaunchStates> fsm, LaunchFSM target) : base(fsm, target) {
    }

    public override void OnEnter() {
        this.SendCommand(new OpenDownloadResCommand());
        ChangeState();
    }
    
    private void ChangeState() {
        mFSM.ChangeState(LaunchStates.InitGameConfig);
    }

    public IArchitecture GetArchitecture() {
        return CrazyCar.Interface;
    }
}
