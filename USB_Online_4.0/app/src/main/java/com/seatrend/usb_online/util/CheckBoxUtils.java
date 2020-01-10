package com.seatrend.usb_online.util;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

public class CheckBoxUtils {

    public static void setListener(final CheckBox cb1, final CheckBox cb2) {
        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    cb1.setChecked(true);
                    cb2.setChecked(false);
                } else {
                    cb1.setChecked(false);
                    cb2.setChecked(true);
                }
            }
        });
        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    cb2.setChecked(true);
                    cb1.setChecked(false);
                } else {
                    cb2.setChecked(false);
                    cb1.setChecked(true);
                }
            }
        });
    }

    public static void setMucherListener(final CheckBox cb1, final CheckBox cb2,final CheckBox cb3){
        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    cb2.setChecked(false);
                    cb3.setChecked(false);
                }
            }
        });

        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    cb3.setChecked(false);
                    cb1.setChecked(false);
                }
            }
        });

        cb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    cb2.setChecked(false);
                    cb1.setChecked(false);
                }
            }
        });
    }
    public static void setMucherListenerBus(final CheckBox cb1, final CheckBox cb2,final CheckBox cb3,final CheckBox cb4){
        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    cb2.setChecked(false);
                    cb3.setChecked(false);
                    cb4.setChecked(false);
                }
            }
        });

        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    cb3.setChecked(false);
                    cb1.setChecked(false);
                    cb4.setChecked(false);
                }
            }
        });

        cb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    cb2.setChecked(false);
                    cb1.setChecked(false);
                    cb4.setChecked(false);
                }
            }
        });

        cb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    cb2.setChecked(false);
                    cb1.setChecked(false);
                    cb3.setChecked(false);
                }
            }
        });
    }

    public static void setListenerAndView(final CheckBox cb1, final CheckBox cb2, final View view) {
        if (null == view) {
            cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (checked) {
                        cb1.setChecked(true);
                        cb2.setChecked(false);
                    } else {
                        cb1.setChecked(false);
                        cb2.setChecked(true);
                    }
                }
            });
            cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (checked) {
                        cb2.setChecked(true);
                        cb1.setChecked(false);
                    } else {
                        cb2.setChecked(false);
                        cb1.setChecked(true);
                    }
                }
            });
        } else if (view instanceof Spinner) {
            cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (checked) {
                        cb1.setChecked(true);
                        cb2.setChecked(false);
                        view.setVisibility(View.INVISIBLE);
                    }
                }
            });
            cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (checked) {
                        cb2.setChecked(true);
                        cb1.setChecked(false);
                        view.setVisibility(View.VISIBLE);
                    }else {
                        view.setVisibility(View.INVISIBLE);
                    }
                }
            });
        } else {
            cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (checked) {
                        cb1.setChecked(true);
                        cb2.setChecked(false);
                        view.setVisibility(View.GONE);
                    } else {
                        cb1.setChecked(false);
                        cb2.setChecked(true);
                        view.setVisibility(View.VISIBLE);
                    }
                }
            });
            cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (checked) {
                        cb2.setChecked(true);
                        cb1.setChecked(false);
                        view.setVisibility(View.VISIBLE);
                    } else {
                        cb2.setChecked(false);
                        cb1.setChecked(true);
                        view.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
    public static void setListenerAndView(final CheckBox cb1, final CheckBox cb2, final View view1,final View view2) {
        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    cb1.setChecked(true);
                    cb2.setChecked(false);
                    view1.setVisibility(View.VISIBLE);
                } else {
                    cb1.setChecked(false);
                    cb2.setChecked(true);
                    view1.setVisibility(View.GONE);
                    view2.setVisibility(View.GONE);
                }
            }
        });
        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    cb2.setChecked(true);
                    cb1.setChecked(false);
                    view1.setVisibility(View.GONE);
                    view2.setVisibility(View.GONE);
                } else {
                    cb2.setChecked(false);
                    cb1.setChecked(true);
                    view1.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}

