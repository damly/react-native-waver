//
//  WaverViewManager.m
//  RNWaver
//
//  Created by damly on 2018/1/2.
//  Copyright © 2018年 damly. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "WaverViewManager.h"
#import "Waver.h"

@implementation WaverViewManager

RCT_EXPORT_MODULE();

- (UIView *)view
{
  //  Waver * waver = [[Waver alloc] init];
  //  waver.waverLevelCallback = ^(Waver * waver) {
  //       waver.level = 0;
 //   };
    
  //  return waver;
    
    return [[Waver alloc] init];
}

RCT_EXPORT_VIEW_PROPERTY(value, CGFloat);

RCT_EXPORT_VIEW_PROPERTY(color, UIColor)

@end
