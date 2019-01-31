    public void addGuest(){
        log.log(Level.INFO,"add new guest");
        EditGuestBean bean=new EditGuestBean();
        this.guestBean=orderService.initGuestBean(bean,(EditGuestBean) null);
        state="guest";
    }


    public void addRoom(){
        log.log(Level.INFO,"add new room order");
        EditRoomOrderBean bean=new EditRoomOrderBean();
        this.roomBean=orderService.initRoomOrderBean(bean,(EditRoomOrderBean) null);
        this.initFeatures();
//        this.roomFeatures.setTarget(roomBean.getFeatures());
        state="room-order";
    }
    
    public void editGuest(){
        log.log(Level.INFO, String.format("edit guest %s", guestBean.getName()));
    //        this.guestBean=orderService.initGuestBean(guestBean,(EditGuestBean) null);
        state="guest";
    }


    public void editRoom(){
        log.log(Level.INFO, String.format("edit room order %s", roomBean.toString()));
        initFeatures();
        state="room-order";
    }
    
    public void saveGuest(EditGuestBean guestBean){
        log.log(Level.INFO,"save guest");
        if (!orderBean.getGuests().contains(guestBean)){
            orderBean.getGuests().add(orderService.initGuestBean(new EditGuestBean(),guestBean));
        }
        state="order";
    }

